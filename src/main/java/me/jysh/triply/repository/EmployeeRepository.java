package me.jysh.triply.repository;

import me.jysh.triply.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByCompanyIdAndUsername(final Long companyEntityId, final String username);

    Optional<EmployeeEntity> findByUsernameAndPassword(final String username, final String password);
}