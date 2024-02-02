package me.jysh.triply.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a mileage upload row for a company fleet.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"employeeId", "distanceTravelledInKm", "energyConsumed", "fuelConsumed"})
public class CompanyFleetMileageUploadEntry {

  /**
   * The unique identifier of the employee associated with the mileage entry.
   */
  private String employeeId;

  /**
   * The distance traveled by the vehicle (in kilometers).
   */
  private Double distanceTravelledInKm;

  /**
   * The energy consumed by the vehicle during the journey (in kilowatt-hours).
   */
  private Double energyConsumed;

  /**
   * The fuel consumed by the vehicle during the journey (in liters).
   */
  private Double fuelConsumed;
}
