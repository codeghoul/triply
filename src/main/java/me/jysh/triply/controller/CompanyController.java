package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyController {

    private final CompanyFacade companyFacade;

    @Operation(
            summary = "Upload employees for a company",
            description = "Upload a csv file containing employee data."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Employees uploaded successfully",
            content = @Content(mediaType = "application/json")
    )
    @PostMapping(value = "/{companyId}/employees:upload", consumes = "multipart/form-data")
    private ResponseEntity<List<EmployeeEntry>> uploadEmployees(
            @PathVariable("companyId") final Long companyId,
            @Parameter(description = "Employee data CSV file", required = true, content = @Content(mediaType = "multipart/form-data"))
            @RequestParam("file") final MultipartFile file
    ) {
        final List<EmployeeEntry> employees = companyFacade.uploadEmployees(companyId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }

    @Operation(
            summary = "Upload emissions for a company",
            description = "Upload a csv file containing emissions data."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Emissions uploaded successfully",
            content = @Content(mediaType = "application/json")
    )
    @PostMapping(value = "/{companyId}/employees/mileage:upload", consumes = "multipart/form-data")
    private ResponseEntity<List<MileageEntry>> uploadEmissions(
            @PathVariable("companyId") final Long companyId,
            @Parameter(description = "Emissions data CSV file", required = true, content = @Content(mediaType = "multipart/form-data"))
            @RequestParam("file") final MultipartFile file,
            @Parameter(required = true)
            @RequestParam("year") final Year year,
            @Parameter(required = true)
            @RequestParam("month") final Month month,
            @Parameter(required = true)
            @RequestParam("week") final Integer week
    ) {
        final List<MileageEntry> mileages = companyFacade.uploadEmission(companyId, year, month, week, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(mileages);
    }
}
