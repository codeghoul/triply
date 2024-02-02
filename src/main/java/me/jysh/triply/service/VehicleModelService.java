package me.jysh.triply.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.dtos.VehicleModelMileageSummaryEntry;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.repository.VehicleModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing vehicle models.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelService {

  private final VehicleModelRepository repository;

  public List<VehicleModelEntry> saveAll(final List<VehicleModelEntity> vehicleModels) {
    final List<VehicleModelEntity> savedEntities = repository.saveAll(vehicleModels);
    return savedEntities.stream().map(VehicleModelMapper::toEntry).toList();
  }

  /**
   * Retrieves a map of vehicle model names to corresponding {@link VehicleModelEntity} instances.
   *
   * @param modelName Collection of vehicle model names to be retrieved.
   * @return Map where keys are vehicle model names and values are corresponding
   * {@link VehicleModelEntity} instances.
   */
  public Map<String, VehicleModelEntity> getVehicleModelEntityMap(
      final Collection<String> modelName) {
    return repository.findAllByNameIn(modelName)
        .stream()
        .collect(Collectors.toMap(VehicleModelEntity::getName, Function.identity()));
  }

  /**
   * Retrieves a list of electric vehicle suggestions based on minimum and maximum distance
   * criteria.
   *
   * @param minDistance Minimum distance criteria for electric vehicle suggestions.
   * @param maxDistance Maximum distance criteria for electric vehicle suggestions.
   * @return List of {@link VehicleModelMileageSummaryEntry} instances representing electric vehicle
   * suggestions.
   */
  public List<VehicleModelMileageSummaryEntry> getElectricVehicleSuggestions(
      final double minDistance, final double maxDistance) {
    final List<VehicleModelMileageSummary> suggestions = repository.getVehicleModelSuggestions(
        minDistance, maxDistance);
    return suggestions.stream()
        .map(VehicleModelMapper::toEntry)
        .toList();
  }
}
