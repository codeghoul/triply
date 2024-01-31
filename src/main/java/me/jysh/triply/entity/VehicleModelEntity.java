package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jysh.triply.dtos.enums.FuelType;

/**
 * Represents a vehicle model entity in the system.
 */
@Entity
@Table(name = "vehicle_model", uniqueConstraints = {
    @UniqueConstraint(name = "uk_vehicle_model", columnNames = {"model"})
})
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleModelEntity extends BaseEntity {

  /**
   * The unique identifier for the vehicle model.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The fuel type of the vehicle model.
   */
  @Enumerated(EnumType.STRING)
  private FuelType fuelType;

  /**
   * The make (brand) of the vehicle model.
   */
  @Column(name = "make")
  private String make;

  /**
   * The name of the vehicle model.
   */
  @Column(name = "name")
  private String name;
}