package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Month;
import java.time.Year;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class CompanyController {

  private final CompanyFacade companyFacade;

  @Operation(
      summary = "Upload employees for a company",
      description = "Upload a CSV file containing employee data."
  )
  @ApiResponse(
      responseCode = "201",
      description = "Employees uploaded successfully",
      content = @Content(mediaType = "application/json")
  )
  @PostMapping(value = "/{companyId}/employees:upload", consumes = "multipart/form-data")
  @PreAuthorize(withRoles = {Constants.ROLE_SUPER_ADMIN})
  private ResponseEntity<List<EmployeeEntry>> uploadEmployees(
      @PathVariable("companyId") final Long companyId,
      @Parameter(description = "Employee data CSV file", required = true, content = @Content(mediaType = "multipart/form-data"))
      @RequestParam("file") final MultipartFile file
  ) {
    log.info("Received employee upload request for company ID: {}", companyId);

    final List<EmployeeEntry> employees = companyFacade.uploadEmployees(companyId, file);

    log.info("Employee data uploaded successfully for company ID: {}", companyId);
    return ResponseEntity.status(HttpStatus.CREATED).body(employees);
  }

  @Operation(
      summary = "Create a company."
  )
  @ApiResponse(
      responseCode = "201",
      description = "Company created successfully",
      content = @Content(mediaType = "application/json")
  )
  @PostMapping(value = "", consumes = "application/json")
  @PreAuthorize(withRoles = {Constants.ROLE_SUPER_ADMIN})
  private ResponseEntity<CompanyEntry> createCompany(
      @RequestBody final CompanyEntry company) {
    log.info("Received request to create a new company");

    final CompanyEntry createdCompany = companyFacade.createCompany(company);

    log.info("Company created successfully with ID: {}", createdCompany.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
  }

  @Operation(
      summary = "Upload vehicle mileages for a company",
      description = "Upload a CSV file containing vehicle mileages data."
  )
  @ApiResponse(
      responseCode = "201",
      description = "Mileages uploaded successfully",
      content = @Content(mediaType = "application/json")
  )
  @PostMapping(value = "/{companyId}/employees/mileage:upload", consumes = "multipart/form-data")
  @PreAuthorize(withRoles = {Constants.ROLE_COMPANY_ADMIN})
  private ResponseEntity<List<MileageEntry>> uploadMileages(
      @PathVariable("companyId") final Long companyId,
      @Parameter(description = "Mileages data CSV file", required = true, content = @Content(mediaType = "multipart/form-data"))
      @RequestParam("file") final MultipartFile file,
      @Parameter(required = true)
      @RequestParam("year") final Year year,
      @Parameter(required = true)
      @RequestParam("month") final Month month,
      @Parameter(required = true)
      @RequestParam("week") final Integer week
  ) {
    log.info("Received vehicle mileages upload request for company ID: {}", companyId);

    final List<MileageEntry> mileages = companyFacade.uploadMileages(companyId, year, month, week,
        file);

    log.info("Emissions data uploaded successfully for company ID: {}", companyId);
    return ResponseEntity.status(HttpStatus.CREATED).body(mileages);
  }
}