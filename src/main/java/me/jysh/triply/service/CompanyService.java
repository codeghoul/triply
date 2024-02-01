package me.jysh.triply.service;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.entity.CompanyEntity;
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
}
