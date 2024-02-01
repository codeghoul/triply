package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.MileageEntry;
import me.jysh.triply.entity.MileageEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class MileageMapper {

  public static MileageEntry toEntry(final MileageEntity entity) {
    final MileageEntry mileageEntry = new MileageEntry();
    mileageEntry.setId(entity.getId());
    mileageEntry.setYear(entity.getYear());
    mileageEntry.setMonth(entity.getMonth());
    mileageEntry.setWeek(entity.getWeek());
    mileageEntry.setDistanceTravelled(entity.getDistanceTravelledInKm());
    mileageEntry.setEnergyConsumed(entity.getEnergyConsumed());
    mileageEntry.setFuelConsumed(entity.getFuelConsumed());
    mileageEntry.setTotalEmission(entity.getTotalEmission());
    mileageEntry.setVehicleId(entity.getVehicleId());
    return mileageEntry;
  }
}
