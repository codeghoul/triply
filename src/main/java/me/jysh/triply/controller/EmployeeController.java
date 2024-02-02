package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Month;
import java.time.Year;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class EmployeeController {

  private final EmployeeService employeeService;

  @Operation(
      summary = "Get emission summary for the employee."
  )
  @ApiResponse(
      responseCode = "200",
      description = "Employee emission summary retrieved successfully",
      content = @Content(mediaType = "application/json")
  )
  @GetMapping(value = "/emissions:summary", produces = "application/json")
  @PreAuthorize(withRoles = {Constants.ROLE_COMPANY_EMPLOYEE})
  private ResponseEntity<EmployeeEmissionSummaryEntry> getEmployeeEmissionSummary(
      @RequestParam(value = "year", required = false) final Year year,
      @RequestParam(value = "month", required = false) final Month month,
      @RequestParam(value = "week", required = false) final Integer week
  ) {
    log.info("Received request to get employee emission summary.");
    final Long employeeId = SecurityContext.getLoggedInEmployeeId();
    final Long company = SecurityContext.getLoggedInEmployeeCompanyId();
    final EmployeeEmissionSummaryEntry summary = employeeService.getEmployeeEmissionSummary(
        employeeId, company, year, month, week);

    log.info("Employee emission summary retrieved successfully with ID: {}", employeeId);
    return ResponseEntity.status(HttpStatus.OK).body(summary);
  }
}
