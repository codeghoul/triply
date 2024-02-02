package me.jysh.triply.service;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.mappers.MileageMapper;
import me.jysh.triply.repository.MileageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with {@link MileageEntity} repository.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MileageService {

  private final MileageRepository repository;

  public List<MileageEntry> saveAll(final List<MileageEntity> mileageEntities) {
    return repository.saveAll(mileageEntities).stream().map(MileageMapper::toEntry).toList();
  }

  public Map<Long, MileageEntity> findAllByTimeAndVehicleIdIn(final Year year, final Month month,
      final Integer week, final Collection<Long> vehicleIds) {
    return repository.findAllByYearAndMonthAndWeekAndVehicleIdIn(year, month, week, vehicleIds)
        .stream()
        .collect((Collectors.toMap(MileageEntity::getVehicleId, Function.identity())));
  }
}
