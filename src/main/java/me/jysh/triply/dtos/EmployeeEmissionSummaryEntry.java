package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.projections.EmployeeEmissionSummary;

/**
 * Data Transfer Object (DTO) representing an employee emission summary entry corresponding to a
 * {@link EmployeeEmissionSummary}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEmissionSummaryEntry {

  /**
   * The total number of vehicles associated with the employee.
   */
  private Long totalVehicles;

  /**
   * The total distance traveled by all vehicles associated with the employee.
   */
  private Double totalDistanceTravelled;

  /**
   * The total energy consumed by all vehicles associated with the employee.
   */
  private Double totalEnergyConsumed;

  /**
   * The total fuel consumed by all vehicles associated with the employee.
   */
  private Double totalFuelConsumed;

  /**
   * The total emission produced by all vehicles associated with the employee.
   */
  private Double totalEmission;
}
