package me.jysh.triply.facade;

import java.time.Month;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.SuggestionsEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.exception.UnauthorizedException;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.VehicleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Facade class for handling employee-related operations. Acts as an intermediary between the
 * controller and service layers for employee-related functionality.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeFacade {

  private final EmployeeService employeeService;

  private final VehicleModelService vehicleModelService;

  /**
   * Retrieves the emission summary for a specific employee based on the provided parameters.
   *
   * @param employeeId The ID of the employee for whom the emission summary is requested.
   * @param year       The year for which the emission summary is requested.
   * @param month      The month for which the emission summary is requested.
   * @param week       The week for which the emission summary is requested.
   * @return An instance of {@link EmployeeEmissionSummaryEntry} representing the employee's
   * emission summary.
   */
  public EmployeeEmissionSummaryEntry getEmployeeEmissionSummary(final Long employeeId,
      final Year year,
      final Month month,
      final Integer week) {
    validateAccess(employeeId);
    final Long companyId = SecurityContext.getLoggedInEmployeeCompanyId();
    return employeeService.getEmployeeEmissionSummary(employeeId, companyId, year, month, week);
  }

  /**
   * Validates that the user has access to the requested employee data.
   *
   * @param employeeId The ID of the employee to be validated.
   * @throws UnauthorizedException If the logged-in user does not have access to the requested
   *                               employee data.
   */
  private static void validateAccess(Long employeeId) {
    final Long loggedInEmployeeId = SecurityContext.getLoggedInEmployeeId();
    if (!Objects.equals(employeeId, loggedInEmployeeId)) {
      throw new UnauthorizedException("User doesn't have the required access for this API.");
    }
  }

  /**
   * Retrieves a list of electric vehicle suggestions for a specific employee based on their mileage
   * summaries.
   *
   * @param employeeId The ID of the employee for whom electric vehicle suggestions are requested.
   * @return List of {@link SuggestionsEntry} instances containing mileage summaries and
   * corresponding electric vehicle suggestions.
   */
  public List<SuggestionsEntry> getElectricVehicleSuggestions(final Long employeeId) {
    validateAccess(employeeId);
    final List<VehicleModelMileageSummaryEntry> employeeMileageSummaries = employeeService.getVehicleModelsMileageSummaries(
        employeeId);

    return employeeMileageSummaries.stream()
        .map(summary -> {
          double minDistance = summary.getAvgDistanceTravelled() - Constants.DISTANCE_RANGE;
          double maxDistance = summary.getAvgDistanceTravelled() + Constants.DISTANCE_RANGE;
          List<VehicleModelMileageSummaryEntry> suggestionBasedOnDistance = vehicleModelService.getElectricVehicleSuggestions(
              minDistance, maxDistance);
          List<VehicleModelMileageSummaryEntry> finalSuggestions = suggestionBasedOnDistance.stream()
              .filter(suggestion -> suggestion.getAvgEmission() < summary.getAvgEmission())
              .sorted(Comparator.comparingDouble(VehicleModelMileageSummaryEntry::getAvgEmission))
              .collect(Collectors.toList());
          return new SuggestionsEntry(summary, finalSuggestions);
        })
        .collect(Collectors.toList());
  }
}
