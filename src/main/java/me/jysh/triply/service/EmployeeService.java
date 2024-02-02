package me.jysh.triply.service;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.projections.EmployeeEmissionSummary;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for interacting with {@link EmployeeEntity} repository.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeService {

  private final EmployeeRepository repository;

  @Transactional
  public List<EmployeeEntry> saveAll(final List<EmployeeEntity> employees) {
    final List<EmployeeEntity> savedEmployees = repository.saveAll(employees);
    return savedEmployees.stream().map(EmployeeMapper::toEntry).toList();
  }

  public EmployeeEntry findByEmployeeIdAndPassword(final String username, final String password) {
    final Optional<EmployeeEntity> optionalEmployee = repository.findByUsernameAndPassword(username,
        password);

    if (optionalEmployee.isEmpty()) {
      throw new NotFoundException(username);
    }

    return optionalEmployee.map(EmployeeMapper::toEntry).get();
  }

  public EmployeeEntry findById(final Long employeeId) {
    return repository.findById(employeeId).map(EmployeeMapper::toEntry).orElseThrow(
        () -> new NotFoundException(String.format("Employee with id :: %d not found", employeeId))
    );
  }

  public Map<String, EmployeeEntity> getCompanyEmployeeByUsernames(final Long companyId,
      final Set<String> employeeUsernames) {
    return repository.findAllByCompanyIdAndUsernameIn(companyId, employeeUsernames)
        .stream().collect(Collectors.toMap(EmployeeEntity::getUsername, employee -> employee));
  }

  /**
   * Retrieves the emission summary for a specific employee based on the provided parameters.
   *
   * @param employeeId The ID of the employee for whom the emission summary is requested.
   * @param companyId  The ID of the company for whom the emission summary is requested.
   * @param year       The year for which the emission summary is requested. Can be null.
   * @param month      The month for which the emission summary is requested. Can be null.
   * @param week       The week for which the emission summary is requested Can be null.
   * @return An {@link EmployeeEmissionSummaryEntry} representing the emission summary for the
   * employee.
   */
  public EmployeeEmissionSummaryEntry getEmployeeEmissionSummary(final Long employeeId,
      final Long companyId,
      final Year year,
      final Month month,
      final Integer week) {

    final Integer yearVal = year == null ? null : year.getValue();
    final String monthVal = month == null ? null : month.name();

    final EmployeeEmissionSummary summary = repository.getEmployeeEmissionSummary(
        companyId, employeeId, yearVal, monthVal, week);

    return EmployeeMapper.toEntry(summary);
  }

  public List<VehicleModelMileageSummaryEntry> getVehicleModelsMileageSummaries(
      final Long employeeId) {
    final List<VehicleModelMileageSummary> summaries = repository.getEmployeeVehicleModelsMileageSummaries(
        employeeId);
    return summaries.stream().map(VehicleModelMapper::toEntry).toList();
  }
}
