package me.jysh.triply.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import me.jysh.triply.constant.Queries;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.projections.EmployeeEmissionSummary;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

  Optional<EmployeeEntity> findByCompanyIdAndUsername(final Long companyEntityId,
      final String username);

  Optional<EmployeeEntity> findByUsernameAndPassword(final String username, final String password);

  List<EmployeeEntity> findAllByCompanyIdAndUsernameIn(final Long companyId,
      final Set<String> employees);

  @Query(value = Queries.GET_EMPLOYEE_EMISSION_SUMMARY, nativeQuery = true)
  EmployeeEmissionSummary getEmployeeEmissionSummary(
      @Param("company_id") final Long companyId,
      @Param("employee_id") final Long employeeId,
      @Param("year") final Integer year,
      @Param("month") final String month,
      @Param("week") final Integer week);

  @Query(value = Queries.GET_EMPLOYEE_VEHICLE_MODELS_MILEAGE_SUMMARIES, nativeQuery = true)
  List<VehicleModelMileageSummary> getEmployeeVehicleModelsMileageSummaries(
      @Param("employee_id") final Long employeeId);
}
