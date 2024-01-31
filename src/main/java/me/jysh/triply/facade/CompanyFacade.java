package me.jysh.triply.facade;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.*;
import me.jysh.triply.entity.*;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mappers.CompanyMapper;
import me.jysh.triply.service.*;
import me.jysh.triply.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyFacade {

    private final CompanyService companyService;

    private final EmployeeService employeeService;

    private final VehicleModelService vehicleModelService;

    private final RoleService roleService;

    private final MileageService mileageService;

    private CompanyEntry createCompany(final CompanyEntry company) {
        final CompanyEntity companyEntity = CompanyMapper.toEntity(company);
        return companyService.save(companyEntity);
    }

    @Transactional
    private List<EmployeeEntry> uploadEmployees(final Long companyId, final MultipartFile file) {
        try {
            final CompanyEntry companyEntry = companyService.findById(companyId);

            final Map<String, RoleEntity> companyRoles = roleService.getRoleEntityMap(Constants.COMPANY_ROLES);

            final List<CompanyFleetUploadEntry> entries = CsvUtils.multipartFileToEntry(file, CompanyFleetUploadEntry.class);
            final Map<String, VehicleModelEntity> vehicleModelMap = getVehicleModels(entries);

            final List<EmployeeEntity> employeeEntities = new ArrayList<>();
            for (CompanyFleetUploadEntry entry : entries) {
                final EmployeeEntity employeeEntity = toEmployeeEntry(companyEntry.getId(), entry, vehicleModelMap, companyRoles);
                employeeEntities.add(employeeEntity);
            }

            return employeeService.saveAll(employeeEntities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    private List<MileageEntry> uploadEmission(final Long companyId, final Year year, final Month month, final Integer week, final MultipartFile file) {
        try {
            final CompanyEntry companyEntry = companyService.findById(companyId);

            final List<CompanyFleetMileageUploadEntry> entries = CsvUtils.multipartFileToEntry(file, CompanyFleetMileageUploadEntry.class);

            final Set<String> employeeUsernames = entries.stream().map(CompanyFleetMileageUploadEntry::getEmployeeID).collect(Collectors.toSet());
            final Map<String, EmployeeEntity> employees = employeeService.getCompanyEmployeeByUsernames(companyEntry.getId(), employeeUsernames);
            if (employeeUsernames.size() > employees.keySet().size()) {
                final Set<String> unregisteredEmployees = employeeUsernames.stream()
                        .filter(model -> !employees.containsKey(model)).collect(Collectors.toSet());
                throw new NotFoundException(String.format("Some employees not found :: %s", unregisteredEmployees));
            }

            final Set<Long> vehicleIds = employees.values().stream().map(employee -> employee.getVehicle().getId()).collect(Collectors.toSet());
            final Map<Long, MileageEntity> existingMileage = mileageService.findAllByTimeAndVehicleIdIn(year, month, week, vehicleIds);


            final List<MileageEntity> toStore = new ArrayList<>();
            for (CompanyFleetMileageUploadEntry entry : entries) {
                final EmployeeEntity employee = employees.get(entry.getEmployeeID());
                final VehicleEntity vehicle = employee.getVehicle();
                final MileageEntity mileageEntity = existingMileage.getOrDefault(vehicle.getId(), new MileageEntity());
                mileageEntity.setYear(year);
                mileageEntity.setMonth(month);
                mileageEntity.setWeek(week);
                mileageEntity.setEnergyConsumed(entry.getEnergyConsumed());
                mileageEntity.setFuelConsumed(entry.getFuelConsumed());
                mileageEntity.setDistanceTravelled(entry.getDistanceTravelled());
                toStore.add(mileageEntity);
            }

            return mileageService.saveAll(toStore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, VehicleModelEntity> getVehicleModels(final List<CompanyFleetUploadEntry> entries) {
        final Set<String> vehicleModels = entries.stream().map(CompanyFleetUploadEntry::getVehicleModel).collect(Collectors.toSet());
        final Map<String, VehicleModelEntity> vehicleModelMap = vehicleModelService.getVehicleModelEntityMap(vehicleModels);
        if (vehicleModels.size() > vehicleModelMap.keySet().size()) {
            Set<String> unsupportedModels = vehicleModels.stream()
                    .filter(model -> !vehicleModelMap.containsKey(model)).collect(Collectors.toSet());
            throw new NotFoundException(String.format("Some vehicle models are not yet supported :: %s", unsupportedModels));
        }
        return vehicleModelMap;
    }

    private static EmployeeEntity toEmployeeEntry(final Long companyId, final CompanyFleetUploadEntry entry, final Map<String, VehicleModelEntity> vehicles, final Map<String, RoleEntity> eligibleRoles) {
        final EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setCompanyId(companyId);
        employeeEntity.setUsername(entry.getEmployeeID());
        employeeEntity.setPassword(entry.getPassword());
        if (entry.getAdmin()) {
            employeeEntity.setRoles(Set.of(eligibleRoles.get(Constants.COMPANY_ADMIN_ROLE)));
        } else {
            employeeEntity.setRoles(Set.of(eligibleRoles.get(Constants.COMPANY_EMPLOYEE_ROLE)));
        }

        final VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setRegistrationNumber(entry.getRegistrationNumber());
        final VehicleModelEntity vehicleModel = vehicles.get(entry.getVehicleModel());
        vehicleEntity.setVehicleModel(vehicleModel);

        vehicleEntity.setEmployee(employeeEntity);
        employeeEntity.setVehicle(vehicleEntity);

        return employeeEntity;
    }
}