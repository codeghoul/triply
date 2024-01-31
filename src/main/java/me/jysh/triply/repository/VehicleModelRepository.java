package me.jysh.triply.repository;

import me.jysh.triply.entity.VehicleModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModelEntity, Long> {

    List<VehicleModelEntity> findAllByNameIn(final Collection<String> names);
}
