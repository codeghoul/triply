package me.jysh.triply.dtos;

import java.time.Month;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MileageEntry {

  private Long id;

  private Long vehicleId;

  private Double distanceTravelled;

  private Double energyConsumed;

  private Double fuelConsumed;

  private Year year;

  private Month month;

  private int week;
}
