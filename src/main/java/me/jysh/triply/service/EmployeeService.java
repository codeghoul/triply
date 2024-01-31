package me.jysh.triply.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeService {

  private final EmployeeRepository repository;

  @Transactional
  public List<EmployeeEntry> saveAll(final List<EmployeeEntity> employees) {
    return repository.saveAll(employees).stream().map(EmployeeMapper::toEntry).toList();
  }

  public EmployeeEntry findByEmployeeIdAndPassword(final String username, final String password) {
    final Optional<EmployeeEntity> optionalEmployee = repository.findByUsernameAndPassword(username,
        password);

    if (optionalEmployee.isEmpty()) {
      throw new NotFoundException(username);
    }

    return optionalEmployee.map(EmployeeMapper::toEntry).get();
  }

  public EmployeeEntry findById(final Long employeeId) {
    return repository.findById(employeeId).map(EmployeeMapper::toEntry).orElseThrow(
        () -> new NotFoundException(String.format("Employee with id :: %d not found", employeeId))
    );
  }

  public Map<String, EmployeeEntity> getCompanyEmployeeByUsernames(final Long companyId,
      final Set<String> employeeUsernames) {
    return repository.findAllByCompanyIdAndUsernameIn(companyId, employeeUsernames)
        .stream().collect(Collectors.toMap(EmployeeEntity::getUsername, employee -> employee));
  }
}
