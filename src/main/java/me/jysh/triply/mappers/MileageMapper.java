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
        mileageEntry.setDistanceTravelled(entity.getDistanceTravelled());
        mileageEntry.setEnergyConsumed(entity.getEnergyConsumed());
        mileageEntry.setFuelConsumed(entity.getFuelConsumed());
        return mileageEntry;
    }
}
