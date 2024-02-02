package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;

/**
 * Data Transfer Object (DTO) representing a vehicle model mileage summary entry corresponding to a
 * {@link VehicleModelMileageSummary}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModelMileageSummaryEntry {

  /**
   * The fuel type of the vehicle.
   */
  private FuelType fuelType;

  /**
   * The make (manufacturer) of the vehicle.
   */
  private String make;

  /**
   * The name or model of the vehicle.
   */
  private String name;

  /**
   * The average distance travelled by the vehicle.
   */
  private Double avgDistanceTravelled;

  /**
   * The average emission produced by the vehicle.
   */
  private Double avgEmission;
}