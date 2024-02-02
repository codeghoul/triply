package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Month;
import java.time.Year;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.SuggestionsEntry;
import me.jysh.triply.facade.EmployeeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class EmployeeController {

  private final EmployeeFacade employeeFacade;

  @Operation(
      summary = "Get emission summary for the employee."
  )
  @ApiResponse(
      responseCode = "200",
      description = "Employee emission summary retrieved successfully",
      content = @Content(mediaType = "application/json")
  )
  @GetMapping(value = "/{employeeId}/emissions:summary", produces = "application/json")
  @PreAuthorize(withRoles = {Constants.ROLE_COMPANY_EMPLOYEE})
  private ResponseEntity<EmployeeEmissionSummaryEntry> getEmployeeEmissionSummary(
      @PathVariable(value = "employeeId") final Long employeeId,
      @RequestParam(value = "year", required = false) final Year year,
      @RequestParam(value = "month", required = false) final Month month,
      @RequestParam(value = "week", required = false) final Integer week
  ) {
    log.info("Received request to get employee emission summary.");
    final EmployeeEmissionSummaryEntry summary = employeeFacade.getEmployeeEmissionSummary(
        employeeId, year, month, week);

    log.info("Emission summary  retrieved successfully for employee with ID: {}",
        employeeId);
    return ResponseEntity.status(HttpStatus.OK).body(summary);
  }

  @Operation(
      summary = "Get vehicle suggestions for the employee."
  )
  @ApiResponse(
      responseCode = "200",
      description = "Employee suggestions retrieved successfully",
      content = @Content(mediaType = "application/json")
  )
  @GetMapping(value = "/{employeeId}/vehicle-model/suggestions", produces = "application/json")
  @PreAuthorize(withRoles = {Constants.ROLE_COMPANY_EMPLOYEE})
  private ResponseEntity<List<SuggestionsEntry>> getEmployeeEmissionSummary(
      @PathVariable(value = "employeeId") final Long employeeId
  ) {
    log.info("Received request to get vehicle suggestions for the employee.");
    final List<SuggestionsEntry> vehicleSuggestions = employeeFacade.getElectricVehicleSuggestions(
        employeeId);

    log.info("Vehicle suggestions retrieved successfully for employee with ID: {}", employeeId);
    return ResponseEntity.status(HttpStatus.OK).body(vehicleSuggestions);
  }
}
