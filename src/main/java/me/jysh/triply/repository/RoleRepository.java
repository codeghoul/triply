package me.jysh.triply.repository;

import me.jysh.triply.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(final String name);

    List<RoleEntity> findAllByNameIn(Collection<String> roles);
}
