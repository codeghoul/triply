package me.jysh.triply.repository;

import java.util.Optional;
import me.jysh.triply.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

  Optional<CompanyEntity> findByName(final String name);
}
