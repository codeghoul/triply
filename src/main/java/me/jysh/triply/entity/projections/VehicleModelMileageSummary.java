package me.jysh.triply.entity.projections;

import me.jysh.triply.dtos.enums.FuelType;
import org.springframework.beans.factory.annotation.Value;

public interface VehicleModelMileageSummary {

  @Value("#{target.fuel_type}")
  FuelType getFuelType();

  @Value("#{target.make}")
  String getMake();

  @Value("#{target.name}")
  String getName();

  @Value("#{target.avg_distance_travelled}")
  Double getAvgDistanceTravelled();

  @Value("#{target.avg_emission}")
  Double getAvgEmission();
}