package me.jysh.triply.facade;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.dtos.VehicleModelEntry;
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
   * @return A list of VehicleModelEntry objects created from the CSV file.
   */
  @Transactional
  private List<VehicleModelEntry> upload(final MultipartFile uploadedFile) {
    try {
      final List<VehicleModelEntry> vehicleModelEntries = CsvUtils.multipartFileToEntry(
          uploadedFile, VehicleModelEntry.class);
      List<VehicleModelEntry> savedModels = vehicleModelService.saveAll(vehicleModelEntries);
      log.info("Uploaded {} vehicle models", savedModels.size());
      return savedModels;
    } catch (IOException e) {
      log.error("Error occurred during vehicle model upload: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}