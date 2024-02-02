package me.jysh.triply.constant;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Queries for the project.
 */
@NoArgsConstructor(onConstructor_ = {@Autowired})
public final class Queries {

  public static final String GET_COMPANY_EMISSION_SUMMARY = """
          SELECT
              ROUND(count(distinct te.id), 2) AS total_employees,
              ROUND(count(distinct tv.id), 2) AS total_vehicles,
              ROUND(sum(tm.distance_travelled), 2) AS total_distance_travelled,
              ROUND(sum(tm.energy_consumed), 2) AS total_energy_consumed,
              ROUND(sum(tm.fuel_consumed), 2) AS total_fuel_consumed,
              ROUND(sum(tm.total_emission), 2) AS total_emission
          FROM
              company tc
                  LEFT JOIN
              employee te ON tc.id = te.company_id
                  LEFT JOIN
              vehicle tv ON tv.employee_id = te.id
                  LEFT JOIN
              mileage tm ON tm.vehicle_id = tv.id
          WHERE
              tc.id = :company_id
              AND (tm.week = :week OR :week IS NULL)
              AND (tm.year = :year OR :year IS NULL)
              AND (tm.month = :month OR :month IS NULL)
      """;

  public static final String GET_EMPLOYEE_EMISSION_SUMMARY = """
          SELECT
              ROUND(count(distinct tv.id), 2) AS total_vehicles,
              ROUND(sum(tm.distance_travelled), 2) AS total_distance_travelled,
              ROUND(sum(tm.energy_consumed), 2) AS total_energy_consumed,
              ROUND(sum(tm.fuel_consumed), 2) AS total_fuel_consumed,
              ROUND(sum(tm.total_emission), 2) AS total_emission
          FROM
              employee te
                  LEFT JOIN
              vehicle tv ON tv.employee_id = te.id
                  LEFT JOIN
              mileage tm ON tm.vehicle_id = tv.id
          WHERE
              te.company_id = :company_id
              AND te.id = :employee_id
              AND (tm.week = :week OR :week IS NULL)
              AND (tm.year = :year OR :year IS NULL)
              AND (tm.month = :month OR :month IS NULL)
      """;
}
