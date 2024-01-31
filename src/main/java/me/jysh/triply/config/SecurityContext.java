package me.jysh.triply.config;

import me.jysh.triply.dtos.EmployeeEntry;

import java.util.Set;

public class SecurityContext {
    private static final ThreadLocal<Long> LOGGED_IN_EMPLOYEE_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> LOGGED_IN_EMPLOYEE_COMPANY_ID = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> LOGGED_IN_EMPLOYEE_ROLES = new ThreadLocal<>();

    public static void setSecurityContext(final EmployeeEntry employee) {
        LOGGED_IN_EMPLOYEE_ID.set(employee.getId());
    }

    public static Long getLoggedInEmployeeId() {
        return LOGGED_IN_EMPLOYEE_ID.get();
    }

    public static String getLoggedInEmployeeCompanyId() {
        return LOGGED_IN_EMPLOYEE_COMPANY_ID.get();
    }

    public static Set<String> getRoles() {
        return LOGGED_IN_EMPLOYEE_ROLES.get();
    }
}
