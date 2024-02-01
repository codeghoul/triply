package me.jysh.triply.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.repository.VehicleModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelService {

  private final VehicleModelRepository repository;

  public List<VehicleModelEntry> saveAll(final List<VehicleModelEntity> vehicleModels) {
    final List<VehicleModelEntity> savedEntities = repository.saveAll(vehicleModels);
    return savedEntities.stream().map(VehicleModelMapper::toEntry).toList();
  }

  public Map<String, VehicleModelEntity> getVehicleModelEntityMap(
      final Collection<String> modelName) {
    return repository.findAllByNameIn(modelName)
        .stream()
        .collect(Collectors.toMap(VehicleModelEntity::getName, Function.identity()));
  }
}
