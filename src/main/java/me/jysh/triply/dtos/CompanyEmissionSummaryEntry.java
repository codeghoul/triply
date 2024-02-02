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

  /**
   * The total number of employees associated with the company.
   */
  private Long totalEmployees;

  /**
   * The total number of vehicles associated with the company.
   */
  private Long totalVehicles;

  /**
   * The total distance traveled by all vehicles associated with the company.
   */
  private Double totalDistanceTravelled;

  /**
   * The total energy consumed by all vehicles associated with the company.
   */
  private Double totalEnergyConsumed;

  /**
   * The total fuel consumed by all vehicles associated with the company.
   */
  private Double totalFuelConsumed;

  /**
   * The total emission produced by all vehicles associated with the company.
   */
  private Double totalEmission;
}
