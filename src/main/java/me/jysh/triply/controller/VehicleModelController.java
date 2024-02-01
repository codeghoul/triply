package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.facade.VehicleModelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class VehicleModelController {

  private final VehicleModelFacade vehicleModelFacade;

  @Operation(
      summary = "Upload vehicle models.",
      description = "Upload a CSV file vehicle models."
  )
  @ApiResponse(
      responseCode = "201",
      description = "Vehicle models uploaded successfully",
      content = @Content(mediaType = "application/json")
  )
  @PostMapping(value = "/vehicle-models:upload", consumes = "multipart/form-data")
  @PreAuthorize(withRoles = {Constants.ROLE_SUPER_ADMIN})
  private ResponseEntity<List<VehicleModelEntry>> uploadVehicleModels(
      @Parameter(description = "Vehicle models data CSV file", required = true, content = @Content(mediaType = "multipart/form-data"))
      @RequestParam("file") final MultipartFile file
  ) {
    log.info("Received vehicle models upload request");

    final List<VehicleModelEntry> vehicleModelEntries = vehicleModelFacade.upload(file);

    log.info("Vehicle models data uploaded successfully for {} models", vehicleModelEntries.size());
    return ResponseEntity.status(HttpStatus.CREATED).body(vehicleModelEntries);
  }
}
