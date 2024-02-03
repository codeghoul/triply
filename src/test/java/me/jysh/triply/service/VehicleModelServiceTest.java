package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.VehicleModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = VehicleModelService.class)
@ExtendWith(SpringExtension.class)
class VehicleModelServiceTest {

  @MockBean
  private VehicleModelRepository repository;

  @Autowired
  private VehicleModelService vehicleModelService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveAll() {
    List<VehicleModelEntity> vehicleModels = TestMocks.getVehicleModelEntities();

    when(repository.saveAll(vehicleModels)).thenReturn(vehicleModels);

    List<VehicleModelEntry> result = vehicleModelService.saveAll(vehicleModels);

    assertNotNull(result);
    assertEquals(vehicleModels.size(), result.size());
  }

  @Test
  void testGetVehicleModelEntityMap() {
    Collection<String> modelNames = Arrays.asList("Model1", "Model2");

    when(repository.findAllByNameIn(modelNames)).thenReturn(TestMocks.getVehicleModelEntities());

    Map<String, VehicleModelEntity> result = vehicleModelService.getVehicleModelEntityMap(
        modelNames);

    assertNotNull(result);
    assertEquals(modelNames.size(), result.size());
  }

  @Test
  void testGetElectricVehicleSuggestions() {
    double minDistance = 100.0;
    double maxDistance = 200.0;

    when(repository.getVehicleModelSuggestions(minDistance, maxDistance)).thenReturn(Arrays.asList(
        TestMocks.getVehicleModelMileageSummary(),
        TestMocks.getVehicleModelMileageSummary())
    );

    List<VehicleModelMileageSummaryEntry> result = vehicleModelService.getElectricVehicleSuggestions(
        minDistance, maxDistance);

    assertNotNull(result);
    assertEquals(2, result.size());
  }
}
