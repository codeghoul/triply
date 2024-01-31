package me.jysh.triply.controller;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/{companyId}/employees:upload", consumes = "multipart/form-data")
    private ResponseEntity<List<EmployeeEntry>> uploadEmployees(@PathVariable("companyId") final Long companyId, @RequestParam("file") final MultipartFile file) {
        final List<EmployeeEntry> employees = companyFacade.uploadEmployees(companyId, file);
        return ResponseEntity.ok(employees);
    }

    @PostMapping(value = "/{companyId}/employees/mileage:upload", consumes = "multipart/form-data")
    private ResponseEntity<List<MileageEntry>> uploadEmissions(@PathVariable("companyId") final Long companyId,
                                                               @RequestParam("file") final MultipartFile file,
                                                               @RequestParam("year") final Year year,
                                                               @RequestParam("month") final Month month,
                                                               @RequestParam("week") final Integer week) {
        final List<MileageEntry> mileages = companyFacade.uploadEmission(companyId, year, month, week, file);
        return ResponseEntity.ok(mileages);
    }
}
