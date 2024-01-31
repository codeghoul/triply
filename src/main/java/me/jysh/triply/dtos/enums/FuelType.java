package me.jysh.triply.dtos.enums;

import lombok.Getter;

/**
 * FuelType enum, can be used for emission calculations.
 */
@Getter
public enum FuelType {
  REGULAR_GASOLINE,
  PREMIUM_GASOLINE,
  DIESEL,
  ETHANOL,
  NATURAL_GAS,
  ELECTRIC,
  HYBRID
}