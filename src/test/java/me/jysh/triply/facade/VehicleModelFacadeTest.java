package me.jysh.triply.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.service.VehicleModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = VehicleModelFacade.class)
@ExtendWith(SpringExtension.class)
class VehicleModelFacadeTest {

  @MockBean
  private VehicleModelService vehicleModelService;

  @Autowired
  private VehicleModelFacade vehicleModelFacade;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUpload_Successful() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        "make,name,fuelType,emissionPerKm\nBrand1,Model1,HYDROGEN,100.0\n".getBytes()
    );

    VehicleModelEntry vehicleModelEntry = new VehicleModelEntry();
    vehicleModelEntry.setName("Model1");
    vehicleModelEntry.setFuelType(FuelType.HYDROGEN);
    vehicleModelEntry.setMake("Brand1");
    vehicleModelEntry.setEmissionPerKm(100.0);

    VehicleModelEntity existingEntity = VehicleModelMapper.toEntity(vehicleModelEntry);
    existingEntity.setId(1L);

    when(vehicleModelService.getVehicleModelEntityMap(any())).thenReturn(
        Collections.singletonMap("Model1", existingEntity));
    when(vehicleModelService.saveAll(any())).thenReturn(
        Collections.singletonList(vehicleModelEntry));

    List<VehicleModelEntry> result = vehicleModelFacade.upload(mockMultipartFile);

    assertEquals(1, result.size());
    assertEquals(vehicleModelEntry, result.get(0));
    verify(vehicleModelService, times(1)).getVehicleModelEntityMap(any());
    verify(vehicleModelService, times(1)).saveAll(any());
  }

  @Test
  void testUpload_Exception() throws IOException {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "file", "test.csv", "text/csv",
        "invalid_csv_data".getBytes()
    );

    when(vehicleModelService.getVehicleModelEntityMap(any())).thenThrow(
        DataIntegrityViolationException.class);

    assertThrows(DataIntegrityViolationException.class,
        () -> vehicleModelFacade.upload(mockMultipartFile));
    verify(vehicleModelService, times(1)).getVehicleModelEntityMap(any());
  }
}
