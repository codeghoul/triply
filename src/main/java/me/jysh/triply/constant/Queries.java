package me.jysh.triply.constant;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Queries for the project.
 */
@NoArgsConstructor(onConstructor_ = {@Autowired})
public final class Queries {

  /**
   * Query to retrieve company emission summary based on various filters.
   */
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

  /**
   * Query to retrieve employee emission summary based on various filters.
   */
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

  /**
   * Query to retrieve electric vehicle suggestions based on distance range.
   */
  public static final String GET_ELECTRIC_VEHICLE_SUGGESTIONS = """
          SELECT
              fuel_type,
              make,
              name,
              ROUND(AVG(m.distance_travelled)) AS avg_distance_travelled,
              ROUND(AVG(m.total_emission), 2) AS avg_emission
          FROM
              vehicle_model vm
                  LEFT JOIN
              vehicle v ON v.vehicle_model_id = vm.id
                  LEFT JOIN
              mileage m ON m.vehicle_id = v.id
          WHERE
              vm.fuel_type = 'ELECTRIC'
          GROUP BY vm.fuel_type , vm.make , vm.name
          HAVING avg_distance_travelled BETWEEN :low AND :high
      """;

  /**
   * Query to retrieve vehicle models' mileage summaries for a specific employee.
   */
  public static final String GET_EMPLOYEE_VEHICLE_MODELS_MILEAGE_SUMMARIES = """
          SELECT
              fuel_type,
              make,
              name,
              ROUND(AVG(m.distance_travelled)) AS avg_distance_travelled,
              ROUND(AVG(m.total_emission), 2) AS avg_emission
          FROM
              employee e
                  LEFT JOIN
              vehicle v ON e.id = v.employee_id
                  LEFT JOIN
              vehicle_model vm ON v.vehicle_model_id = vm.id
                  LEFT JOIN
              mileage m ON m.vehicle_id = v.id
          WHERE
            e.id = :employee_id
          GROUP BY E.id, vm.fuel_type , vm.make , vm.name;
      """;
}
