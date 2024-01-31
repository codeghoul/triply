package me.jysh.triply.repository;

import me.jysh.triply.entity.MileageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;

@Repository
public interface MileageRepository extends JpaRepository<MileageEntity, Long> {

    List<MileageEntity> findAllByYearAndMonthAndWeekAndVehicleIdIn(final Year year, final Month month, final Integer week, final Collection<Long> vehicleIds);
}
