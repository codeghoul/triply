package me.jysh.triply.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.entity.VehicleModelEntity;

/**
 * Data Transfer Object (DTO) representing a vehicle model entry corresponding to a
 * {@link VehicleModelEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"make", "name", "fuelType", "emissionPerKm"})
public class VehicleModelEntry {

  /**
   * The unique identifier of the vehicle model.
   */
  private Long id;

  /**
   * The make (brand or manufacturer) of the vehicle model.
   */
  private String make;

  /**
   * The name of the vehicle model.
   */
  private String name;

  /**
   * The type of fuel used by the vehicle model.
   */
  private FuelType fuelType;

  /**
   * The emission per km for this vehicle type in grams.
   */
  private Double emissionPerKm;
}