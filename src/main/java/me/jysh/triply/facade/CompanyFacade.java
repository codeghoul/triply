package me.jysh.triply.facade;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.CompanyEmissionSummaryEntry;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.dtos.CompanyFleetMileageUploadEntry;
import me.jysh.triply.dtos.CompanyFleetUploadEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.VehicleEntity;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.exception.BadRequestException;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.exception.UnauthorizedException;
import me.jysh.triply.mappers.CompanyMapper;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.service.CompanyService;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.MileageService;
import me.jysh.triply.service.RoleService;
import me.jysh.triply.service.VehicleModelService;
import me.jysh.triply.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyFacade {

  private final CompanyService companyService;

  private final EmployeeService employeeService;

  private final VehicleModelService vehicleModelService;

  private final RoleService roleService;

  private final MileageService mileageService;

  private final PasswordEncoder passwordEncoder;

  /**
   * Creates a new company.
   *
   * @param company The CompanyEntry to create.
   * @return The created CompanyEntry.
   */
  @Transactional
  public CompanyEntry createCompany(final CompanyEntry company) {
    final CompanyEntity companyEntity = CompanyMapper.toEntity(company);
    return companyService.save(companyEntity);
  }

  /**
   * Uploads a list of employees for a company from a CSV file.
   *
   * @param companyId The ID of the company.
   * @param file      The CSV file containing employee data.
   * @return A list of EmployeeEntry objects created from the CSV file.
   */
  @Transactional
  public List<EmployeeEntry> uploadEmployees(final Long companyId, final MultipartFile file) {
    try {
      final CompanyEntry companyEntry = companyService.findById(companyId);

      final Map<String, RoleEntity> companyRoles = roleService.getRoleEntityMap(
          Constants.COMPANY_ROLES);

      final List<CompanyFleetUploadEntry> entries = CsvUtils.multipartFileToEntry(file,
          CompanyFleetUploadEntry.class);
      final Map<String, VehicleModelEntity> vehicleModelMap = getVehicleModels(entries);

      final List<EmployeeEntity> employeeEntities = new ArrayList<>();
      for (CompanyFleetUploadEntry entry : entries) {
        EmployeeEntity employee = EmployeeMapper.toEmployeeEntry(companyEntry.getId(), entry,
            vehicleModelMap, companyRoles);
        employee.setPassword(passwordEncoder.encode(entry.getPassword()));
        employeeEntities.add(employee);
      }

      return employeeService.saveAll(employeeEntities);
    } catch (IOException e) {
      log.error("Error occurred during employee upload for company with ID {}: {}", companyId,
          e.getMessage());
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      log.error("Error occurred during employee upload for company with ID {}: {}", companyId,
          e.getMessage());
      throw e;
    }
  }

  /**
   * Uploads mileage data for a company's fleet from a CSV file.
   *
   * @param companyId The ID of the company.
   * @param year      The year of the mileage data.
   * @param month     The month of the mileage data.
   * @param week      The week of the mileage data.
   * @param file      The CSV file containing mileage data.
   * @return A list of MileageEntry objects created from the CSV file.
   */
  @Transactional
  public List<MileageEntry> uploadMileages(final Long companyId, final Year year, final Month month,
      final Integer week, final MultipartFile file) {
    validateUploadAccess(companyId);

    try {
      final CompanyEntry companyEntry = companyService.findById(companyId);

      final List<CompanyFleetMileageUploadEntry> entries = CsvUtils.multipartFileToEntry(file,
          CompanyFleetMileageUploadEntry.class);

      final Map<String, EmployeeEntity> employees = getExistingEmployeesForMileageUpload(
          entries, companyEntry);

      final Set<Long> vehicleIds = employees.values().stream()
          .map(employee -> employee.getVehicle().getId()).collect(Collectors.toSet());
      final Map<Long, MileageEntity> existingMileage = mileageService.findAllByTimeAndVehicleIdIn(
          year, month, week, vehicleIds);

      final List<MileageEntity> toStore = new ArrayList<>();
      for (CompanyFleetMileageUploadEntry entry : entries) {
        final EmployeeEntity employee = employees.get(entry.getEmployeeId());
        final VehicleEntity vehicle = employee.getVehicle();
        final MileageEntity mileageEntity = existingMileage.getOrDefault(vehicle.getId(),
            new MileageEntity());
        updateMileageEntity(year, month, week, entry, mileageEntity, vehicle);
        toStore.add(mileageEntity);
      }

      return mileageService.saveAll(toStore);
    } catch (IOException e) {
      log.error("Error occurred during mileage upload for company with ID {}: {}", companyId,
          e.getMessage());
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      log.error("Error occurred during employee upload for company with ID {}: {}", companyId,
          e.getMessage());
      throw e;
    }
  }

  private Map<String, EmployeeEntity> getExistingEmployeesForMileageUpload(
      List<CompanyFleetMileageUploadEntry> entries, CompanyEntry companyEntry) {
    final Set<String> employeeUsernames = entries.stream()
        .map(CompanyFleetMileageUploadEntry::getEmployeeId).collect(Collectors.toSet());
    final Map<String, EmployeeEntity> employees = employeeService.getCompanyEmployeeByUsernames(
        companyEntry.getId(), employeeUsernames);
    if (employeeUsernames.size() > employees.keySet().size()) {
      final Set<String> unregisteredEmployees = employeeUsernames.stream()
          .filter(model -> !employees.containsKey(model)).collect(Collectors.toSet());
      throw new NotFoundException(
          String.format("Some employees not found :: %s", unregisteredEmployees));
    }
    return employees;
  }

  private static void updateMileageEntity(Year year, Month month, Integer week,
      CompanyFleetMileageUploadEntry entry, MileageEntity mileageEntity, VehicleEntity vehicle) {
    mileageEntity.setYear(year);
    mileageEntity.setMonth(month);
    mileageEntity.setWeek(week);
    mileageEntity.setEnergyConsumed(entry.getEnergyConsumed());
    mileageEntity.setFuelConsumed(entry.getFuelConsumed());
    mileageEntity.setDistanceTravelledInKm(entry.getDistanceTravelledInKm());
    mileageEntity.setTotalEmission(
        entry.getDistanceTravelledInKm() * vehicle.getVehicleModel().getEmissionPerKm());
    mileageEntity.setVehicleId(vehicle.getId());
  }

  private void validateUploadAccess(final Long companyId) {
    if (Objects.equals(SecurityContext.getLoggedInEmployeeCompanyId(), companyId)) {
      return;
    }
    throw new UnauthorizedException("User does not belong to this company.");
  }

  private Map<String, VehicleModelEntity> getVehicleModels(
      final List<CompanyFleetUploadEntry> entries) {
    final Set<String> vehicleModels = entries.stream().map(CompanyFleetUploadEntry::getVehicleModel)
        .collect(Collectors.toSet());
    final Map<String, VehicleModelEntity> vehicleModelMap = vehicleModelService.getVehicleModelEntityMap(
        vehicleModels);
    if (vehicleModels.size() > vehicleModelMap.keySet().size()) {
      Set<String> unsupportedModels = vehicleModels.stream()
          .filter(model -> !vehicleModelMap.containsKey(model)).collect(Collectors.toSet());
      throw new NotFoundException(
          String.format("Some vehicle models are not yet supported :: %s", unsupportedModels));
    }
    return vehicleModelMap;
  }

  public CompanyEmissionSummaryEntry getCompanyEmissionSummary(final Long companyId,
      final Year year, final Month month, final Integer week) {
    return companyService.getCompanyEmissionSummary(companyId, year, month, week);
  }
}
