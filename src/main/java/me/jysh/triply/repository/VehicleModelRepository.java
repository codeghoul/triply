package me.jysh.triply.repository;

import java.util.Collection;
import java.util.List;
import me.jysh.triply.constant.Queries;
import me.jysh.triply.entity.VehicleModelEntity;
import me.jysh.triply.entity.projections.VehicleModelMileageSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModelEntity, Long> {

  List<VehicleModelEntity> findAllByNameIn(final Collection<String> names);

  @Query(value = Queries.GET_ELECTRIC_VEHICLE_SUGGESTIONS, nativeQuery = true)
  List<VehicleModelMileageSummary> getVehicleModelSuggestions(
      @Param("low") final Double minDistance,
      @Param("high") final Double maxDistance);
}
