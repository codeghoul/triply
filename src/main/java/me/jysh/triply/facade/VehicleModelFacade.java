package me.jysh.triply.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.exception.BadRequestException;
import me.jysh.triply.mappers.VehicleModelMapper;
import me.jysh.triply.service.VehicleModelService;
import me.jysh.triply.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelFacade {

  private final VehicleModelService vehicleModelService;

  /**
   * Uploads a list of vehicle models from a CSV file.
   *
   * @param uploadedFile The CSV file containing vehicle model data.
   * @return A list of VehicleModelEntry objects created / updated from the CSV file.
   */
  @Transactional
  public List<VehicleModelEntry> upload(final MultipartFile uploadedFile) {
    try {
      final List<VehicleModelEntry> vehicleModelEntries = CsvUtils.multipartFileToEntry(
          uploadedFile, VehicleModelEntry.class);

      final Set<String> vehicleModels = vehicleModelEntries.stream().map(VehicleModelEntry::getName)
          .collect(Collectors.toSet());

      final Map<String, VehicleModelEntity> existingVehicleModelsMap = vehicleModelService.getVehicleModelEntityMap(
          vehicleModels);

      final List<VehicleModelEntity> toUpsert = new ArrayList<>();

      for (VehicleModelEntry entry : vehicleModelEntries) {
        final VehicleModelEntity vehicleModelEntity = existingVehicleModelsMap.getOrDefault(
            entry.getName(),
            VehicleModelMapper.toEntity(entry));

        vehicleModelEntity.setEmissionPerKm(entry.getEmissionPerKm());
        vehicleModelEntity.setMake(entry.getMake());
        vehicleModelEntity.setFuelType(entry.getFuelType());

        toUpsert.add(vehicleModelEntity);
      }

      List<VehicleModelEntry> savedModels = vehicleModelService.saveAll(toUpsert);
      log.info("Uploaded {} vehicle models", savedModels.size());
      return savedModels;
    } catch (IOException e) {
      log.error("Error occurred during vehicle model upload: {}", e.getMessage());
      throw new BadRequestException(e.getMessage());
    }
  }
}