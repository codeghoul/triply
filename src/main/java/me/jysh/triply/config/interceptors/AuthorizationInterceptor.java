package me.jysh.triply.config.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Set;
import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * The {@code AuthorizationInterceptor} class is an implementation of Spring's
 * {@code HandlerInterceptor} designed for handling authorization checks before allowing the
 * execution of a request handler method. It checks for the presence of the {@code PreAuthorize}
 * annotation on the target handler method and verifies if the current user possesses the required
 * roles specified in the annotation.
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

  /**
   * This method is called before the execution of the handler method. It checks for the presence of
   * {@code PreAuthorize} annotation and validates the required roles.
   *
   * @param request  the incoming HTTP request
   * @param response the HTTP response to be sent
   * @param handler  the target handler method
   * @return {@code true} if the user has the required roles or if no authorization check is needed,
   * {@code false} otherwise
   * @throws UnauthorizedException if the user doesn't have the required access for this API
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    if (handler instanceof HandlerMethod handlerMethod) {
      PreAuthorize annotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
      if (annotation == null || annotation.withRoles().length == 0) {
        return true;
      }

      String[] requiredRoles = annotation.withRoles();
      if (hasRequiredRoles(requiredRoles)) {
        return true;
      } else {
        throw new UnauthorizedException("User doesn't have the required access for this API.");
      }
    }

    return true;
  }

  /**
   * Checks if the user possesses the required roles.
   *
   * @param requiredRoles an array of role names required for access
   * @return {@code true} if the user has at least one of the required roles, {@code false}
   * otherwise
   */
  private boolean hasRequiredRoles(String[] requiredRoles) {
    Set<String> roles = SecurityContext.getRoles();
    return roles != null && !roles.isEmpty() &&
        Arrays.stream(requiredRoles).anyMatch(roles::contains);
  }
}
