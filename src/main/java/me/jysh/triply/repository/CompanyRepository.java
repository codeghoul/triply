package me.jysh.triply.repository;

import java.util.Optional;
import me.jysh.triply.constant.Queries;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.projections.CompanyEmissionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

  Optional<CompanyEntity> findByName(final String name);

  @Query(value = Queries.GET_COMPANY_EMISSION_SUMMARY, nativeQuery = true)
  CompanyEmissionSummary getCompanyEmissionSummary(
      @Param("company_id") final Long companyId,
      @Param("year") final Integer year,
      @Param("month") final String month,
      @Param("week") final Integer week);
}
