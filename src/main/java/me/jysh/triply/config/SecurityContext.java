package me.jysh.triply.config;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;

/**
 * Utility class for managing security context information for the logged-in employee.
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class SecurityContext {

  private static final ThreadLocal<Long> LOGGED_IN_EMPLOYEE_ID = new ThreadLocal<>();
  private static final ThreadLocal<String> LOGGED_IN_EMPLOYEE_COMPANY_ID = new ThreadLocal<>();
  private static final ThreadLocal<Set<String>> LOGGED_IN_EMPLOYEE_ROLES = new ThreadLocal<>();

  /**
   * Sets the security context for the logged-in employee.
   *
   * @param employee The EmployeeEntry representing the logged-in employee.
   */
  public static void setSecurityContext(final EmployeeEntry employee) {
    LOGGED_IN_EMPLOYEE_ID.set(employee.getId());
  }

  /**
   * Retrieves the ID of the currently logged-in employee.
   *
   * @return The ID of the logged-in employee.
   */
  public static Long getLoggedInEmployeeId() {
    return LOGGED_IN_EMPLOYEE_ID.get();
  }

  /**
   * Retrieves the company ID of the currently logged-in employee.
   *
   * @return The company ID of the logged-in employee.
   */
  public static String getLoggedInEmployeeCompanyId() {
    return LOGGED_IN_EMPLOYEE_COMPANY_ID.get();
  }

  /**
   * Retrieves the roles of the currently logged-in employee.
   *
   * @return The roles of the logged-in employee.
   */
  public static Set<String> getRoles() {
    return LOGGED_IN_EMPLOYEE_ROLES.get();
  }
}