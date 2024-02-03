package me.jysh.triply.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.SuggestionsEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.exception.UnauthorizedException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.VehicleModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = EmployeeFacade.class)
@ExtendWith(SpringExtension.class)
class EmployeeFacadeTest {

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private VehicleModelService vehicleModelService;

  @Autowired
  private EmployeeFacade employeeFacade;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetEmployeeEmissionSummary_Successful() {
    Long employeeId = 1L;
    Year year = Year.of(2022);
    Month month = Month.JANUARY;
    Integer week = 1;

    when(employeeService.getEmployeeEmissionSummary(any(), any(), any(), any(), any())).thenReturn(
        EmployeeMapper.toEntry(TestMocks.getEmployeeEmissionSummary()));

    EmployeeEmissionSummaryEntry result = employeeFacade.getEmployeeEmissionSummary(employeeId,
        year, month, week);

    assertNotNull(result);
    verify(employeeService, times(1)).getEmployeeEmissionSummary(employeeId, 1L,
        year, month, week);
  }

  @Test
  void testGetElectricVehicleSuggestions_Successful() {
    final VehicleModelMileageSummaryEntry employeeVehicleModelSummary = VehicleModelMapper.toEntry(
        TestMocks.getVehicleModelMileageSummary());
    employeeVehicleModelSummary.setFuelType(FuelType.DIESEL);
    employeeVehicleModelSummary.setAvgEmission(60.0);
    List<VehicleModelMileageSummaryEntry> employeeMileageSummaries = List.of(
        employeeVehicleModelSummary);
    final EmployeeEntry entry = EmployeeMapper.toEntry(TestMocks.getEmployeeEntity());
    SecurityContext.setSecurityContext(entry);
    when(employeeService.getVehicleModelsMileageSummaries(entry.getId())).thenReturn(
        employeeMileageSummaries);

    final VehicleModelMileageSummaryEntry vehicleModelMileageSummaryEntry = VehicleModelMapper.toEntry(
        TestMocks.getVehicleModelMileageSummary());
    vehicleModelMileageSummaryEntry.setFuelType(FuelType.ELECTRIC);
    when(vehicleModelService.getElectricVehicleSuggestions(anyDouble(), anyDouble())).thenReturn(
        Collections.singletonList(vehicleModelMileageSummaryEntry));

    List<SuggestionsEntry> result = employeeFacade.getElectricVehicleSuggestions(entry.getId());

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(employeeService, times(1)).getVehicleModelsMileageSummaries(entry.getId());
    verify(vehicleModelService, times(1)).getElectricVehicleSuggestions(anyDouble(), anyDouble());
  }

  @Test
  void testGetEmployeeEmissionSummary_UnauthorizedException() {
    Long employeeId = 1L;
    Year year = Year.of(2022);
    Month month = Month.JANUARY;
    Integer week = 1;

    doThrow(new UnauthorizedException("Unauthorized access")).when(employeeService)
        .getEmployeeEmissionSummary(any(), any(), any(), any(), any());

    assertThrows(UnauthorizedException.class,
        () -> employeeFacade.getEmployeeEmissionSummary(employeeId, year, month, week));
  }

  @Test
  void testGetElectricVehicleSuggestions_UnauthorizedException() {
    Long employeeId = 1L;

    doThrow(new UnauthorizedException("Unauthorized access")).when(employeeService)
        .getVehicleModelsMileageSummaries(employeeId);

    assertThrows(UnauthorizedException.class,
        () -> employeeFacade.getElectricVehicleSuggestions(employeeId));
  }
}
