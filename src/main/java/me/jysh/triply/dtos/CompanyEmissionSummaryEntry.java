package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.projections.CompanyEmissionSummary;

/**
 * Data Transfer Object (DTO) representing a company emission summary entry corresponding to a
 * {@link CompanyEmissionSummary}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmissionSummaryEntry {

  private Long totalEmployees;

  private Long totalVehicles;

  private Double totalDistanceTravelled;

  private Double totalEnergyConsumed;

  private Double totalFuelConsumed;

  private Double totalEmission;
}
