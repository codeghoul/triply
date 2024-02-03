package me.jysh.triply.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.VehicleEntity;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.exception.BadRequestException;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.exception.UnauthorizedException;
import me.jysh.triply.mappers.CompanyMapper;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.mappers.MileageMapper;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.service.CompanyService;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.MileageService;
import me.jysh.triply.service.RoleService;
import me.jysh.triply.service.VehicleModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = CompanyFacade.class)
@ExtendWith(SpringExtension.class)
class CompanyFacadeTest {

  @MockBean
  private CompanyService companyService;

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private VehicleModelService vehicleModelService;

  @MockBean
  private RoleService roleService;

  @MockBean
  private MileageService mileageService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CompanyFacade companyFacade;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    final EmployeeEntry employee = new EmployeeEntry();
    employee.setRoles(new ArrayList<>());
    SecurityContext.setSecurityContext(employee);
  }

  @Test
  void testUploadEmployees_Successful() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        """
              employeeId,password,registrationNumber,vehicleModel,admin
              EMP001,password123,ABC123,Corolla,FALSE
              EMP002,securePass456,ABC124,Accord,TRUE\s
            """.getBytes()
    );

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);

    final List<RoleEntity> roleEntities = TestMocks.getRoleEntities();
    final Map<String, RoleEntity> companyRoles = roleEntities.stream()
        .collect(Collectors.toMap(RoleEntity::getName, Function.identity()));

    Map<String, VehicleModelEntity> vehicleModelMap = Map.of(
        "Corolla", new VehicleModelEntity(),
        "Accord", new VehicleModelEntity()
    );

    final EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();
    final EmployeeEntry entry = EmployeeMapper.toEntry(employeeEntity);

    when(companyService.findById(1L)).thenReturn(companyEntry);
    when(vehicleModelService.getVehicleModelEntityMap(any())).thenReturn(vehicleModelMap);
    when(roleService.getRoleEntityMap(any())).thenReturn(companyRoles);
    when(employeeService.saveAll(any())).thenReturn(List.of(entry));

    List<EmployeeEntry> result = companyFacade.uploadEmployees(1L, mockMultipartFile);

    assertEquals(1, result.size());
    assertEquals(entry.getEmployeeId(), result.get(0).getEmployeeId());
    verify(companyService, times(1)).findById(1L);
    verify(vehicleModelService, times(1)).getVehicleModelEntityMap(any());
    verify(roleService, times(1)).getRoleEntityMap(any());
    verify(employeeService, times(1)).saveAll(any());
  }

  @Test
  void testUploadEmployees_VehicleModelNotFound() throws IOException {
    // Arrange
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        """
              employeeId,password,registrationNumber,vehicleModel,admin
              EMP001,password123,ABC123,Corolla,FALSE
              EMP002,securePass456,ABC124,Accord,TRUE\s
            """.getBytes()
    );

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);

    Map<String, VehicleModelEntity> vehicleModelMap = Map.of(
        "Model1", new VehicleModelEntity()
    );

    when(companyService.findById(1L)).thenReturn(companyEntry);
    when(vehicleModelService.getVehicleModelEntityMap(any())).thenReturn(vehicleModelMap);

    assertThrows(NotFoundException.class,
        () -> companyFacade.uploadEmployees(1L, mockMultipartFile));
  }

  @Test
  void testUploadEmployees_InvalidCSV() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        "invalid_csv_data".getBytes()
    );

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);

    when(companyService.findById(1L)).thenReturn(companyEntry);

    assertThrows(BadRequestException.class,
        () -> companyFacade.uploadEmployees(1L, mockMultipartFile));
    verify(companyService, times(1)).findById(1L);
  }

  @Test
  void testUploadMileages_Successful() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        """
            employeeId,distanceTravelledInKm,energyConsumed,fuelConsumed\s
            john_doe,150,30,15
            """.getBytes()
    );

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);

    Year year = Year.of(2024);
    Month month = Month.JANUARY;
    Integer week = 1;

    final EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();
    final EmployeeEntry employeeEntry = EmployeeMapper.toEntry(employeeEntity);
    Map<String, EmployeeEntity> employees = Map.of(
        "john_doe", employeeEntity
    );
    SecurityContext.setSecurityContext(employeeEntry);

    VehicleEntity vehicle = new VehicleEntity();
    vehicle.setId(1L);

    MileageEntity mileageEntity = TestMocks.getMileageEntity();
    final MileageEntry mileageEntry = MileageMapper.toEntry(mileageEntity);

    when(companyService.findById(1L)).thenReturn(companyEntry);
    when(employeeService.getCompanyEmployeeByUsernames(any(), any())).thenReturn(employees);
    when(mileageService.findAllByTimeAndVehicleIdIn(year, month, week,
        Set.of(vehicle.getId()))).thenReturn(Map.of(vehicle.getId(), mileageEntity));
    when(mileageService.saveAll(any())).thenReturn(List.of(mileageEntry));

    List<MileageEntry> result = companyFacade.uploadMileages(1L, year, month, week,
        mockMultipartFile);

    assertEquals(1, result.size());
    assertEquals(30, result.get(0).getEnergyConsumed());
    verify(companyService, times(1)).findById(1L);
    verify(employeeService, times(1)).getCompanyEmployeeByUsernames(any(), any());
    verify(mileageService, times(1)).findAllByTimeAndVehicleIdIn(year, month, week,
        Set.of(vehicle.getId()));
    verify(mileageService, times(1)).saveAll(any());
  }

  @Test
  void testUploadMileages_Unauthorized() throws IOException {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        """
            employeeId,distanceTravelledInKm,energyConsumed,fuelConsumed\s
            john_doe,150,30,15
            """.getBytes()
    );

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);
    when(companyService.findById(1L)).thenReturn(companyEntry);

    Year year = Year.of(2022);
    Month month = Month.JANUARY;
    Integer week = 1;

    assertThrows(UnauthorizedException.class,
        () -> companyFacade.uploadMileages(1L, year, month, week, mockMultipartFile));
  }

  @Test
  void testUploadMileages_InvalidCSV() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        """
            employeeId,distanceTravelledInKm,energyConsumed,fuelConsumed\s
            """.getBytes()
    );

    final EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();
    final EmployeeEntry employeeEntry = EmployeeMapper.toEntry(employeeEntity);
    SecurityContext.setSecurityContext(employeeEntry);

    CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(1L);

    Year year = Year.of(2022);
    Month month = Month.JANUARY;
    Integer week = 1;

    when(companyService.findById(1L)).thenReturn(companyEntry);

    assertThrows(BadRequestException.class,
        () -> companyFacade.uploadMileages(1L, year, month, week, mockMultipartFile));
    verify(companyService, times(1)).findById(1L);
  }

  @Test
  void testCreateCompany_Successful() {
    final CompanyEntity companyEntity = TestMocks.getCompanyEntity();
    final CompanyEntry companyEntry = CompanyMapper.toEntry(companyEntity);

    when(companyService.save(any())).thenReturn(companyEntry);

    CompanyEntry result = companyFacade.createCompany(companyEntry);

    assertEquals(companyEntity.getName(), result.getName());
    verify(companyService, times(1)).save(any());
  }
}
