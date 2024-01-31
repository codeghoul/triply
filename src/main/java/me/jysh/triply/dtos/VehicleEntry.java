package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.VehicleEntity;

/**
 * Data Transfer Object (DTO) representing a vehicle entry corresponding to a
 * {@link VehicleEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntry {

  /**
   * The unique identifier of the vehicle.
   */
  private Long id;

  /**
   * The registration number of the vehicle.
   */
  private String registrationNumber;

  /**
   * The model of the vehicle.
   */
  private VehicleModelEntry vehicleModel;

  /**
   * The unique identifier of the employee associated with the vehicle.
   */
  private Long employeeId;
}
