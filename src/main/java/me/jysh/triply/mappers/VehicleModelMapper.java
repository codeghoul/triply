package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.VehicleModelEntry;
import me.jysh.triply.entity.VehicleModelEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class VehicleModelMapper {

  public static VehicleModelEntity toEntity(final VehicleModelEntry entry) {
    final VehicleModelEntity entity = new VehicleModelEntity();
    entity.setId(entry.getId());
    entity.setName(entry.getName());
    entity.setMake(entry.getMake());
    entity.setFuelType(entry.getFuelType());
    entity.setEmissionPerKm(entry.getEmissionPerKm());
    return entity;
  }

  public static VehicleModelEntry toEntry(final VehicleModelEntity entity) {
    final VehicleModelEntry entry = new VehicleModelEntry();
    entry.setId(entity.getId());
    entry.setName(entity.getName());
    entry.setMake(entity.getMake());
    entry.setFuelType(entity.getFuelType());
    entry.setEmissionPerKm(entity.getEmissionPerKm());
    return entry;
  }
}
