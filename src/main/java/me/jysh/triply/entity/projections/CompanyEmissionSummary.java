package me.jysh.triply.entity.projections;

import org.springframework.beans.factory.annotation.Value;

public interface CompanyEmissionSummary {

  @Value("#{target.total_employees}")
  Long getTotalEmployees();

  @Value("#{target.total_vehicles}")
  Long getTotalVehicles();

  @Value("#{target.total_distance_travelled}")
  Double getTotalDistanceTravelled();

  @Value("#{target.total_energy_consumed}")
  Double getTotalEnergyConsumed();

  @Value("#{target.total_fuel_consumed}")
  Double getTotalFuelConsumed();

  @Value("#{target.total_emission}")
  Double getTotalEmission();
}
