package me.jysh.triply.config.interceptors;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.exception.UnauthenticatedException;
import me.jysh.triply.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Autowired
  private final TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    final String bearerToken = request.getHeader("Authorization");
    if (bearerToken == null || bearerToken.length() < 7) {
      throw new UnauthenticatedException("Invalid or missing Auth header");
    }
    final String accessToken = bearerToken.substring(7);
    final boolean isValid = tokenService.validateAccessToken(accessToken);

    if (!isValid) {
      throw new UnauthenticatedException("Invalid or missing Auth header");
    }

    final Claims claims = tokenService.getClaims(accessToken);
    final EmployeeEntry employeeEntry = new EmployeeEntry();
    employeeEntry.setId(claims.get(Constants.EMPLOYEE_ID_CLAIM_KEY, Long.class));
    employeeEntry.setRoles(claims.get(Constants.ROLES_CLAIM_KEY, ArrayList.class));
    employeeEntry.setCompanyId(claims.get(Constants.COMPANY_ID_CLAIM_KEY, Long.class));
    SecurityContext.setSecurityContext(employeeEntry);

    return true;
  }
}
