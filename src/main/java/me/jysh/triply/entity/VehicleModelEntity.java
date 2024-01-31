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

@Data
@Entity
@Table(name = "vehicle_model", uniqueConstraints = {
    @UniqueConstraint(name = "uk_vehicle_model", columnNames = {"model"})
})
@EqualsAndHashCode(callSuper = true)
public class VehicleModelEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private FuelType fuelType;

  private String make;

  private String name;
}
