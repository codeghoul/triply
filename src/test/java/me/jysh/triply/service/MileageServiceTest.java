package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.MileageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = MileageService.class)
@ExtendWith(SpringExtension.class)
class MileageServiceTest {

  @MockBean
  private MileageRepository mileageRepository;

  @Autowired
  private MileageService mileageService;

  @Test
  void testSaveAll() {
    List<MileageEntity> mileageEntities = Arrays.asList(TestMocks.getMileageEntity(),
        TestMocks.getMileageEntity());
    when(mileageRepository.saveAll(mileageEntities)).thenReturn(mileageEntities);
    List<MileageEntry> result = mileageService.saveAll(mileageEntities);
    assertNotNull(result);
    assertEquals(2, result.size());
  }

  @Test
  void testFindAllByTimeAndVehicleIdIn() {
    Year year = Year.of(2023);
    Month month = Month.JANUARY;
    Integer week = 2;
    Collection<Long> vehicleIds = Arrays.asList(1L, 2L);
    final MileageEntity mileageEntity1 = TestMocks.getMileageEntity();
    final MileageEntity mileageEntity2 = TestMocks.getMileageEntity();
    mileageEntity2.setVehicleId(2L);
    when(
        mileageRepository.findAllByYearAndMonthAndWeekAndVehicleIdIn(year, month, week, vehicleIds))
        .thenReturn(Arrays.asList(mileageEntity1, mileageEntity2));
    Map<Long, MileageEntity> result = mileageService.findAllByTimeAndVehicleIdIn(year, month, week,
        vehicleIds);
    assertNotNull(result);
    assertEquals(2, result.size());
  }
}
