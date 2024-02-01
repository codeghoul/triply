package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Month;
import java.time.Year;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jysh.triply.utils.YearAttributeConverter;

/**
 * Represents a mileage entity in the system.
 */
@Data
@Entity
@Table(name = "mileage")
@EqualsAndHashCode(callSuper = true)
public class MileageEntity extends BaseEntity {

  /**
   * The unique identifier for the mileage record.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The identifier of the vehicle for which mileage is recorded.
   */
  @Column(name = "vehicle_id")
  private Long vehicleId;

  /**
   * The distance traveled by the vehicle.
   */
  @Column(name = "distance_travelled")
  private Double distanceTravelledInKm;

  /**
   * The energy consumed by the vehicle.
   */
  @Column(name = "energy_consumed")
  private Double energyConsumed;

  /**
   * The fuel consumed by the vehicle.
   */
  @Column(name = "fuel_consumed")
  private Double fuelConsumed;

  /**
   * The year for which the mileage is recorded.
   */
  @Column(name = "year")
  @Convert(converter = YearAttributeConverter.class)
  private Year year;

  /**
   * The month for which the mileage is recorded.
   */
  @Column(name = "month")
  @Enumerated(EnumType.STRING)
  private Month month;

  /**
   * The week number for which the mileage is recorded.
   */
  @Column(name = "week")
  private Integer week;

  /**
   * Total emission in grams.
   */
  @Column(name = "total_emission")
  private Double totalEmission;
}