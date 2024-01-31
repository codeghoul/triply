package me.jysh.triply.config.interceptors;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.exception.UnauthenticatedException;
import me.jysh.triply.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * Interceptor for handling authentication before processing requests.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

  private final TokenService tokenService;

  /**
   * Pre-handle method executed before the actual handler is invoked, used for checking valid auth
   * header.
   *
   * @param request  The request being processed.
   * @param response The response to be sent to the client.
   * @param handler  The handler (typically a controller) to be invoked.
   * @return {@code true} if the execution chain should proceed; {@code false} if the execution
   * should be stopped.
   * @throws UnauthenticatedException if the authentication fails.
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    final String bearerToken = request.getHeader("Authorization");
    if (bearerToken == null || bearerToken.length() < 7) {
      log.error("Invalid or missing Auth header");
      throw new UnauthenticatedException("Invalid or missing Auth header");
    }

    final String accessToken = bearerToken.substring(7);
    final boolean isValid = tokenService.validateAccessToken(accessToken);

    if (!isValid) {
      log.error("Invalid or missing Auth header");
      throw new UnauthenticatedException("Invalid or missing Auth header");
    }

    final Claims claims = tokenService.getClaims(accessToken);
    final EmployeeEntry employeeEntry = new EmployeeEntry();
    employeeEntry.setId(claims.get(Constants.EMPLOYEE_ID_CLAIM_KEY, Long.class));
    employeeEntry.setRoles(claims.get(Constants.ROLES_CLAIM_KEY, ArrayList.class));
    employeeEntry.setCompanyId(claims.get(Constants.COMPANY_ID_CLAIM_KEY, Long.class));

    SecurityContext.setSecurityContext(employeeEntry);
    log.info("User with ID {} and roles {} authenticated successfully", employeeEntry.getId(),
        employeeEntry.getRoles());

    return true;
  }
}
