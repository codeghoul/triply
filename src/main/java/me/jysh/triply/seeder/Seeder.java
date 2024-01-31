package me.jysh.triply.seeder;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.jysh.triply.entity.CompanyEntity;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.repository.CompanyRepository;
import me.jysh.triply.repository.EmployeeRepository;
import me.jysh.triply.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public final class Seeder implements CommandLineRunner {

  private final CompanyRepository companyRepository;
  private final EmployeeRepository employeeRepository;
  private final RoleRepository roleRepository;

  @Override
  public void run(String... args) {

    final List<RoleEntity> roles = Stream.of("ROLE_SUPER_ADMIN")
        .map(this::getOrCreateRole).toList();

    final CompanyEntity companyEntity = getOrCreateCompany("Triply");

    final EmployeeEntity employeeEntity = getOrCreateEmployee(companyEntity.getId(), "10101",
        roles);
  }

  private EmployeeEntity getOrCreateEmployee(final Long companyId, final String username,
      final List<RoleEntity> roles) {
    return employeeRepository.findByCompanyIdAndUsername(companyId, username)
        .orElseGet(() -> {
          EmployeeEntity newEmployee = new EmployeeEntity();
          newEmployee.setUsername(username);
          newEmployee.setCompanyId(companyId);
          newEmployee.setPassword("some password");
          newEmployee.setRoles(roles);
          return employeeRepository.save(newEmployee);
        });
  }

  private CompanyEntity getOrCreateCompany(final String name) {
    return companyRepository.findByName(name)
        .orElseGet(() -> {
          CompanyEntity newCompany = new CompanyEntity();
          newCompany.setName(name);
          return companyRepository.save(newCompany);
        });
  }

  private RoleEntity getOrCreateRole(final String name) {
    return roleRepository.findByName(name)
        .orElseGet(() -> {
          RoleEntity newRole = new RoleEntity();
          newRole.setName(name);
          return roleRepository.save(newRole);
        });
  }
}
