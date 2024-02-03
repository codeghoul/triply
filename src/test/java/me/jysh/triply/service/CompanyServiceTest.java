package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.Year;
import me.jysh.triply.dtos.CompanyEmissionSummaryEntry;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.projections.CompanyEmissionSummary;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = CompanyService.class)
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

  @MockBean
  private CompanyRepository companyRepository;

  @Autowired
  private CompanyService companyService;

  @Test
  void testFindById() {
    Long companyId = 1L;
    CompanyEntity companyEntity = TestMocks.getCompanyEntity();
    when(companyRepository.findById(companyId)).thenReturn(java.util.Optional.of(companyEntity));
    CompanyEntry result = companyService.findById(companyId);
    assertNotNull(result);
  }

  @Test
  void testFindByIdNotFound() {
    Long companyId = 1L;
    when(companyRepository.findById(companyId)).thenReturn(java.util.Optional.empty());
    assertThrows(NotFoundException.class, () -> companyService.findById(companyId));
  }

  @Test
  void testSave() {
    CompanyEntity companyEntity = TestMocks.getCompanyEntity();
    when(companyRepository.save(any(CompanyEntity.class))).thenReturn(companyEntity);
    CompanyEntry result = companyService.save(companyEntity);
    assertNotNull(result);
  }

  @Test
  void testGetCompanyEmissionSummary() {
    Long companyId = 1L;
    Year year = Year.of(2023);
    Month month = Month.JANUARY;
    Integer week = 2;
    CompanyEmissionSummary companyEmissionSummary = TestMocks.getCompanyEmissionSummary();
    when(companyRepository.getCompanyEmissionSummary(companyId, 2023, "JANUARY", 2))
        .thenReturn(companyEmissionSummary);
    CompanyEmissionSummaryEntry result = companyService.getCompanyEmissionSummary(companyId, year,
        month, week);
    assertNotNull(result);
  }
}
