package me.jysh.triply.constant;

import java.util.Set;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Common constants for the project.
 */
@NoArgsConstructor(onConstructor_ = {@Autowired})
public final class Constants {

  public static final String EMPLOYEE_ID_CLAIM_KEY = "uid";

  public static final String COMPANY_ID_CLAIM_KEY = "cid";

  public static final String ROLES_CLAIM_KEY = "roles";

  public static final String ROLE_COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";

  public static final String ROLE_COMPANY_EMPLOYEE = "ROLE_COMPANY_EMPLOYEE";

  public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

  public static final Set<String> COMPANY_ROLES = Set.of(ROLE_COMPANY_ADMIN, ROLE_COMPANY_EMPLOYEE);

  public static final Double DISTANCE_RANGE = 10D;
}
