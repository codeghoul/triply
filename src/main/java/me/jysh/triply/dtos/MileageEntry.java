package me.jysh.triply.dtos;

import java.time.Month;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.MileageEntity;

/**
 * Data Transfer Object (DTO) representing a mileage entry corresponding to a
 * {@link MileageEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MileageEntry {

  /**
   * The unique identifier of the mileage entry.
   */
  private Long id;

  /**
   * The unique identifier of the vehicle associated with the mileage entry.
   */
  private Long vehicleId;

  /**
   * The distance traveled by the vehicle (in kilometers).
   */
  private Double distanceTravelled;

  /**
   * The energy consumed by the vehicle during the journey (in kilowatt-hours).
   */
  private Double energyConsumed;

  /**
   * The fuel consumed by the vehicle during the journey (in liters).
   */
  private Double fuelConsumed;

  /**
   * The year in which the mileage entry occurred.
   */
  private Year year;

  /**
   * The month in which the mileage entry occurred.
   */
  private Month month;

  /**
   * The week in which the mileage entry occurred.
   */
  private int week;

  /**
   * The total emission in grams.
   */
  private Double totalEmission;
}
