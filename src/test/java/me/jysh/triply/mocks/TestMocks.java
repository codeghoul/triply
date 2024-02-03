package me.jysh.triply.mocks;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import me.jysh.triply.dtos.enums.FuelType;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.MileageEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.entity.VehicleEntity;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.entity.projections.CompanyEmissionSummary;
import me.jysh.triply.entity.projections.EmployeeEmissionSummary;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;

public final class TestMocks {

  public static CompanyEntity getCompanyEntity() {
    final CompanyEntity companyEntity = new CompanyEntity();
    companyEntity.setId(1L);
    companyEntity.setName("Triply");
    return companyEntity;
  }

  public static CompanyEmissionSummary getCompanyEmissionSummary() {
    return new CompanyEmissionSummary() {
      @Override
      public Long getTotalEmployees() {
        return 2L;
      }

      @Override
      public Long getTotalVehicles() {
        return 2L;
      }

      @Override
      public Double getTotalDistanceTravelled() {
        return 120D;
      }

      @Override
      public Double getTotalEnergyConsumed() {
        return 12D;
      }

      @Override
      public Double getTotalFuelConsumed() {
        return 12D;
      }

      @Override
      public Double getTotalEmission() {
        return 12D;
      }
    };
  }

  public static EmployeeEmissionSummary getEmployeeEmissionSummary() {
    return new EmployeeEmissionSummary() {
      @Override
      public Long getTotalVehicles() {
        return 10L;
      }

      @Override
      public Double getTotalDistanceTravelled() {
        return 500.0;
      }

      @Override
      public Double getTotalEnergyConsumed() {
        return 100.0;
      }

      @Override
      public Double getTotalFuelConsumed() {
        return 50.0;
      }

      @Override
      public Double getTotalEmission() {
        return 20.0;
      }
    };
  }

  public static VehicleModelMileageSummary getVehicleModelMileageSummary() {
    return new VehicleModelMileageSummary() {
      @Override
      public FuelType getFuelType() {
        return FuelType.DIESEL;
      }

      @Override
      public String getMake() {
        return "Toyota";
      }

      @Override
      public String getName() {
        return "Camry";
      }

      @Override
      public Double getAvgDistanceTravelled() {
        return 150.0;
      }

      @Override
      public Double getAvgEmission() {
        return 30.0;
      }
    };
  }


  public static EmployeeEntity getEmployeeEntity() {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setId(1L);
    employeeEntity.setUsername("john_doe");
    employeeEntity.setPassword("password");
    employeeEntity.setCompanyId(1L);

    Collection<RoleEntity> roles = new ArrayList<>();
    roles.add(getRoleEntity());
    employeeEntity.setRoles(roles);

    final VehicleEntity mockVehicleEntity = getVehicleEntity();
    employeeEntity.setVehicle(mockVehicleEntity);
    mockVehicleEntity.setEmployee(employeeEntity);

    return employeeEntity;
  }

  public static MileageEntity getMileageEntity() {
    MileageEntity mileageEntity = new MileageEntity();
    mileageEntity.setId(1L);
    mileageEntity.setVehicleId(1L);
    mileageEntity.setDistanceTravelledInKm(150.0);
    mileageEntity.setEnergyConsumed(30.0);
    mileageEntity.setFuelConsumed(15.0);
    mileageEntity.setYear(Year.of(2024));
    mileageEntity.setMonth(Month.JANUARY);
    mileageEntity.setWeek(4);
    mileageEntity.setTotalEmission(10.0);
    return mileageEntity;
  }

  public static List<RoleEntity> getRoleEntities() {
    final RoleEntity userRole = new RoleEntity();
    userRole.setId(1L);
    userRole.setName("ROLE_USER");
    final RoleEntity adminRole = new RoleEntity();
    adminRole.setId(2L);
    adminRole.setName("ROLE_ADMIN");
    return Arrays.asList(
        userRole,
        adminRole
    );
  }

  private static RoleEntity getRoleEntity() {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setId(1L);
    roleEntity.setName("ROLE_USER");
    return roleEntity;
  }

  public static VehicleModelEntity getVehicleModelEntity() {
    VehicleModelEntity vehicleModelEntity = new VehicleModelEntity();
    vehicleModelEntity.setId(1L);
    vehicleModelEntity.setFuelType(FuelType.DIESEL);
    vehicleModelEntity.setMake("Toyota");
    vehicleModelEntity.setName("Camry");
    vehicleModelEntity.setEmissionPerKm(120.5);
    return vehicleModelEntity;
  }

  public static List<VehicleModelEntity> getVehicleModelEntities() {
    VehicleModelEntity model1 = new VehicleModelEntity();
    model1.setId(1L);
    model1.setFuelType(FuelType.ELECTRIC);
    model1.setMake("Tesla");
    model1.setName("Model S");
    model1.setEmissionPerKm(50.0);

    VehicleModelEntity model2 = new VehicleModelEntity();
    model2.setId(2L);
    model2.setFuelType(FuelType.HYBRID);
    model2.setMake("Toyota");
    model2.setName("Prius");
    model2.setEmissionPerKm(70.0);

    return Arrays.asList(model1, model2);
  }

  public static VehicleEntity getVehicleEntity() {
    VehicleEntity mockVehicleEntity = new VehicleEntity();
    mockVehicleEntity.setRegistrationNumber("Registration Number");
    final VehicleModelEntity mockVehicleModelEntity = getVehicleModelEntity();
    mockVehicleEntity.setVehicleModel(mockVehicleModelEntity);
    return mockVehicleEntity;
  }
}
