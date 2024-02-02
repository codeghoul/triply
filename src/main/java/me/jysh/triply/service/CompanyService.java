package me.jysh.triply.service;

import java.time.Month;
import java.time.Year;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.CompanyEmissionSummaryEntry;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.projections.CompanyEmissionSummary;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mappers.CompanyMapper;
import me.jysh.triply.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyService {

  private final CompanyRepository companyRepository;

  public CompanyEntry findById(final Long companyId) {
    return companyRepository.findById(companyId).map(CompanyMapper::toEntry)
        .orElseThrow(() -> new NotFoundException(
            String.format("company with id :: %d not found", companyId)));
  }

  @Transactional
  public CompanyEntry save(final CompanyEntity company) {
    return CompanyMapper.toEntry(companyRepository.save(company));
  }

  public CompanyEmissionSummaryEntry getCompanyEmissionSummary(final Long companyId,
      final Year year, final
  Month month, final Integer week) {
    final Integer yearVal = year == null ? null : year.getValue();
    final String monthVal = month == null ? null : month.name();
    final CompanyEmissionSummary companyEmissionSummary = companyRepository.getCompanyEmissionSummary(
        companyId, yearVal, monthVal, week);

    return CompanyMapper.toEntry(companyEmissionSummary);
  }
}
