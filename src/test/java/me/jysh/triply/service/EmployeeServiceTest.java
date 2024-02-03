package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = EmployeeService.class)
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

  @MockBean
  private EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeService employeeService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveAll() {
    List<EmployeeEntity> employees = Collections.singletonList(TestMocks.getEmployeeEntity());
    when(employeeRepository.saveAll(employees)).thenReturn(employees);
    List<EmployeeEntry> result = employeeService.saveAll(employees);
    assertNotNull(result);
  }

  @Test
  void testFindByUsername() {
    String username = "john_doe";
    when(employeeRepository.findByUsername(username)).thenReturn(
        Optional.of(TestMocks.getEmployeeEntity()));
    EmployeeEntity result = employeeService.findByUsername(username);
    assertNotNull(result);
  }

  @Test
  void testFindById() {
    Long employeeId = 1L;
    when(employeeRepository.findById(employeeId)).thenReturn(
        Optional.of(TestMocks.getEmployeeEntity()));
    EmployeeEntry result = employeeService.findById(employeeId);
    assertNotNull(result);
  }

  @Test
  void testGetCompanyEmployeeByUsernames() {
    Long companyId = 1L;
    Set<String> employeeUsernames = Set.of("user1", "user2");
    when(employeeRepository.findAllByCompanyIdAndUsernameIn(companyId, employeeUsernames))
        .thenReturn(Collections.singletonList(TestMocks.getEmployeeEntity()));
    Map<String, EmployeeEntity> result = employeeService.getCompanyEmployeeByUsernames(companyId,
        employeeUsernames);
    assertNotNull(result);
  }

  @Test
  void testGetEmployeeEmissionSummary() {
    Long employeeId = 1L;
    Long companyId = 1L;
    Year year = Year.of(2023);
    Month month = Month.JANUARY;
    Integer week = 2;
    when(employeeRepository.getEmployeeEmissionSummary(companyId, employeeId, 2023, "JANUARY", 2))
        .thenReturn(TestMocks.getEmployeeEmissionSummary());
    EmployeeEmissionSummaryEntry result = employeeService.getEmployeeEmissionSummary(employeeId,
        companyId, year, month, week);
    assertNotNull(result);
  }

  @Test
  void testGetVehicleModelsMileageSummaries() {
    Long employeeId = 1L;
    when(employeeRepository.getEmployeeVehicleModelsMileageSummaries(employeeId)).thenReturn(
        Collections.singletonList(TestMocks.getVehicleModelMileageSummary()));
    List<VehicleModelMileageSummaryEntry> result = employeeService.getVehicleModelsMileageSummaries(
        employeeId);
    assertNotNull(result);
  }
}
