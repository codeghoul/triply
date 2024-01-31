package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a vehicle entity in the system.
 */
@Entity
@Table(name = "vehicle",
    uniqueConstraints = @UniqueConstraint(name = "uk_registration_number", columnNames = "registration_number"))
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleEntity extends BaseEntity {

  /**
   * The unique identifier for the vehicle.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The registration number of the vehicle.
   */
  @Column(name = "registration_number", unique = true)
  private String registrationNumber;

  /**
   * The model of the vehicle.
   */
  @OneToOne
  @JoinColumn(name = "vehicle_model_id")
  private VehicleModelEntity vehicleModel;

  /**
   * The employee associated with the vehicle.
   */
  @OneToOne
  @JoinColumn(name = "employee_id")
  private EmployeeEntity employee;
}
