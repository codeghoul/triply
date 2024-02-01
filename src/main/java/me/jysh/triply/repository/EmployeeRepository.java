package me.jysh.triply.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import me.jysh.triply.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

  Optional<EmployeeEntity> findByCompanyIdAndUsername(final Long companyEntityId,
      final String username);

  Optional<EmployeeEntity> findByUsernameAndPassword(final String username, final String password);

  List<EmployeeEntity> findAllByCompanyIdAndUsernameIn(Long companyId, Set<String> employees);
}
