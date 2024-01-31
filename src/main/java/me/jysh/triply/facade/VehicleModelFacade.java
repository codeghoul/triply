package me.jysh.triply.facade;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.service.VehicleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelFacade {

  private final VehicleModelService vehicleModelService;

  @Transactional
  private List<VehicleModelEntry> upload(final MultipartFile uploadedFile) {
    try {
      final CsvMapper csvMapper = new CsvMapper();
      final VehicleModelEntry[] vehicleModelEntries = csvMapper.readerWithSchemaFor(
              VehicleModelEntry.class)
          .readValue(uploadedFile.getInputStream(), VehicleModelEntry[].class);
      return vehicleModelService.saveAll(List.of(vehicleModelEntries));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
