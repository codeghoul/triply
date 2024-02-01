package me.jysh.triply.facade;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.dtos.CompanyFleetMileageUploadEntry;
import me.jysh.triply.dtos.CompanyFleetUploadEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.VehicleEntity;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.service.CompanyService;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.MileageService;
import me.jysh.triply.service.RoleService;
import me.jysh.triply.service.VehicleModelService;
import me.jysh.triply.utils.CsvUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class CompanyFacadeTest {

  @Mock
  private CompanyService companyService;

  @Mock
  private EmployeeService employeeService;

  @Mock
  private VehicleModelService vehicleModelService;

  @Mock
  private RoleService roleService;

  @Mock
  private MileageService mileageService;

  @InjectMocks
  private CompanyFacade companyFacade;

  @Test
  void createCompany() {
    final CompanyEntry inputCompanyEntry = getMockCompanyEntry();

    CompanyEntry outputCompanyEntry = new CompanyEntry();
    inputCompanyEntry.setName("Triply");
    when(companyService.save(any(CompanyEntity.class))).thenReturn(outputCompanyEntry);

    CompanyEntry resultCompanyEntry = companyFacade.createCompany(inputCompanyEntry);

    Assertions.assertEquals(outputCompanyEntry, resultCompanyEntry);
    verify(companyService, times(1)).save(any(CompanyEntity.class));
  }

  @Test
  void uploadEmployees() throws IOException {
    Long companyId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test.csv", new byte[0]);
    CompanyEntry mockCompanyEntry = new CompanyEntry();
    when(companyService.findById(companyId)).thenReturn(mockCompanyEntry);

    CompanyFleetUploadEntry mockUploadEntry = new CompanyFleetUploadEntry();
    List<CompanyFleetUploadEntry> mockEntries = Collections.singletonList(mockUploadEntry);
    when(CsvUtils.multipartFileToEntry(eq(mockFile), eq(CompanyFleetUploadEntry.class)))
        .thenReturn(mockEntries);

    RoleEntity mockRoleEntity = new RoleEntity();
    Map<String, RoleEntity> mockRoles = Collections.singletonMap(Constants.ROLE_COMPANY_EMPLOYEE,
        mockRoleEntity);
    when(roleService.getRoleEntityMap(eq(Constants.COMPANY_ROLES))).thenReturn(mockRoles);

    VehicleModelEntity mockVehicleModelEntity = new VehicleModelEntity();
    Map<String, VehicleModelEntity> mockVehicleModelMap = Collections.singletonMap("TestModel",
        mockVehicleModelEntity);
    when(vehicleModelService.getVehicleModelEntityMap(Mockito.anyCollection())).thenReturn(
        mockVehicleModelMap);

    EmployeeEntity mockEmployeeEntity = new EmployeeEntity();
    List<EmployeeEntity> mockEmployeeEntities = Collections.singletonList(mockEmployeeEntity);

    final EmployeeEntry employeeEntry = new EmployeeEntry();
    when(employeeService.saveAll(eq(mockEmployeeEntities))).thenReturn(
        Collections.singletonList(employeeEntry));

    List<EmployeeEntry> resultEmployeeEntries = companyFacade.uploadEmployees(companyId, mockFile);

    Assertions.assertEquals(mockEmployeeEntities, resultEmployeeEntries);
    verify(companyService, times(1)).findById(eq(companyId));
    verify(roleService, times(1)).getRoleEntityMap(eq(Constants.COMPANY_ROLES));
    verify(vehicleModelService, times(1)).getVehicleModelEntityMap(
        eq(Collections.singletonList("modelName")));
    verify(employeeService, times(1)).saveAll(eq(mockEmployeeEntities));
  }

  @Test
  void uploadEmission() throws IOException {
    Long companyId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test.csv", new byte[0]);
    Year year = Year.of(2024);
    Month month = Month.JANUARY;
    Integer week = 1;

    final CompanyEntry mockCompanyEntry = getMockCompanyEntry();
    when(companyService.findById(companyId)).thenReturn(mockCompanyEntry);

    final List<CompanyFleetMileageUploadEntry> mockEntries = getMockCompanyFleetMileageUploadEntries();
    when(CsvUtils.multipartFileToEntry(eq(mockFile), eq(CompanyFleetMileageUploadEntry.class)))
        .thenReturn(mockEntries);

    final EmployeeEntity mockEmployeeEntity = getMockEmployeeEntity();

    Map<String, EmployeeEntity> mockEmployees = Collections.singletonMap("TestUser",
        mockEmployeeEntity);
    when(employeeService.getCompanyEmployeeByUsernames(eq(mockCompanyEntry.getId()), anySet()))
        .thenReturn(mockEmployees);

    MileageEntity mockMileageEntity = new MileageEntity();
    List<MileageEntity> mockMileageEntities = Collections.singletonList(mockMileageEntity);
    when(mileageService.saveAll(eq(mockMileageEntities))).thenReturn(mockMileageEntities);

    List<MileageEntry> resultMileageEntries = companyFacade.uploadMileages(companyId, year, month,
        week, mockFile);

    Assertions.assertEquals(mockMileageEntities, resultMileageEntries);
    verify(companyService, times(1)).findById(eq(companyId));
    verify(employeeService, times(1)).getCompanyEmployeeByUsernames(eq(mockCompanyEntry.getId()),
        anySet());
    verify(mileageService, times(1)).saveAll(eq(mockMileageEntities));
  }

  private static EmployeeEntity getMockEmployeeEntity() {
    final EmployeeEntity mockEmployeeEntity = new EmployeeEntity();
    mockEmployeeEntity.setCompanyId(1L);
    mockEmployeeEntity.setUsername("10101");
    final VehicleEntity mockVehicleEntity = getMockVehicleEntity();
    mockEmployeeEntity.setVehicle(mockVehicleEntity);
    mockVehicleEntity.setEmployee(mockEmployeeEntity);
    return mockEmployeeEntity;
  }

  private static VehicleEntity getMockVehicleEntity() {
    VehicleEntity mockVehicleEntity = new VehicleEntity();
    mockVehicleEntity.setRegistrationNumber("Registration Number");
    final VehicleModelEntity mockVehicleModelEntity = getMockVehicleModelEntity();
    mockVehicleEntity.setVehicleModel(mockVehicleModelEntity);
    return mockVehicleEntity;
  }

  private static List<CompanyFleetMileageUploadEntry> getMockCompanyFleetMileageUploadEntries() {
    final CompanyFleetMileageUploadEntry mockUploadEntry = new CompanyFleetMileageUploadEntry();
    mockUploadEntry.setDistanceTravelledInKm(1.0);
    mockUploadEntry.setEnergyConsumed(25.0);
    mockUploadEntry.setFuelConsumed(25.0);
    mockUploadEntry.setEmployeeId("10101");
    return Collections.singletonList(mockUploadEntry);
  }

  private static CompanyEntry getMockCompanyEntry() {
    CompanyEntry mockCompanyEntry = new CompanyEntry();
    mockCompanyEntry.setId(1L);
    mockCompanyEntry.setName("Triply");
    return mockCompanyEntry;
  }

  private static VehicleModelEntity getMockVehicleModelEntity() {
    final VehicleModelEntity mockVehicleModelEntity = new VehicleModelEntity();
    mockVehicleModelEntity.setMake("Volvo");
    mockVehicleModelEntity.setName("Brand");
    mockVehicleModelEntity.setFuelType(FuelType.DIESEL);
    return mockVehicleModelEntity;
  }
}
