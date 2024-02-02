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

  private Long totalVehicles;

  private Double totalDistanceTravelled;

  private Double totalEnergyConsumed;

  private Double totalFuelConsumed;

  private Double totalEmission;
}
