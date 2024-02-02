package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.projections.EmployeeEmissionSummary;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class EmployeeMapper {

  public static EmployeeEntry toEntry(final EmployeeEntity entity) {
    final EmployeeEntry employeeEntry = new EmployeeEntry();
    employeeEntry.setId(entity.getId());
    employeeEntry.setCompanyId(entity.getCompanyId());
    employeeEntry.setEmployeeId(entity.getUsername());
    employeeEntry.setRoles(entity.getRoles().stream().map(RoleEntity::getName).toList());
    return employeeEntry;
  }

  public static EmployeeEmissionSummaryEntry toEntry(final EmployeeEmissionSummary summary) {
    final EmployeeEmissionSummaryEntry entry = new EmployeeEmissionSummaryEntry();
    entry.setTotalVehicles(summary.getTotalVehicles());
    entry.setTotalEmission(summary.getTotalEmission());
    entry.setTotalDistanceTravelled(summary.getTotalDistanceTravelled());
    entry.setTotalFuelConsumed(summary.getTotalFuelConsumed());
    entry.setTotalEnergyConsumed(summary.getTotalEnergyConsumed());
    return entry;
  }
}
