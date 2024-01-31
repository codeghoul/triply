package me.jysh.triply.dtos;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.EmployeeEntity;

/**
 * Data Transfer Object (DTO) representing an employee entry corresponding to a
 * {@link EmployeeEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntry {

  /**
   * The unique identifier of the employee.
   */
  private Long id;

  /**
   * The username of the employee.
   */
  private String employeeId;

  /**
   * The unique identifier of the company to which the employee belongs.
   */
  private Long companyId;

  /**
   * The roles associated with the employee. Roles determine the permissions and access level of the
   * employee.
   */
  private Collection<String> roles;
}
