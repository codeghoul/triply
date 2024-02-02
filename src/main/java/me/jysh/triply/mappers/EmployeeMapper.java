package me.jysh.triply.mappers;

import java.util.HashSet;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.CompanyFleetUploadEntry;
import me.jysh.triply.dtos.EmployeeEmissionSummaryEntry;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.VehicleEntity;
import me.jysh.triply.entity.VehicleModelEntity;
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

  public static EmployeeEntity toEmployeeEntry(final Long companyId,
      final CompanyFleetUploadEntry entry, final Map<String, VehicleModelEntity> vehicles,
      final Map<String, RoleEntity> eligibleRoles) {
    final EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setCompanyId(companyId);
    employeeEntity.setUsername(entry.getEmployeeId());
    employeeEntity.setPassword(entry.getPassword());
    employeeEntity.setRoles(new HashSet<>());
    if (entry.getAdmin()) {
      employeeEntity.getRoles().add(eligibleRoles.get(Constants.ROLE_COMPANY_ADMIN));
    }
    employeeEntity.getRoles().add(eligibleRoles.get(Constants.ROLE_COMPANY_EMPLOYEE));

    final VehicleEntity vehicleEntity = new VehicleEntity();
    vehicleEntity.setRegistrationNumber(entry.getRegistrationNumber());
    final VehicleModelEntity vehicleModel = vehicles.get(entry.getVehicleModel());
    vehicleEntity.setVehicleModel(vehicleModel);

    vehicleEntity.setEmployee(employeeEntity);
    employeeEntity.setVehicle(vehicleEntity);

    return employeeEntity;
  }
}
