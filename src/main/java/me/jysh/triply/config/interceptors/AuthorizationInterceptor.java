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

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod handlerMethod) {
      final PreAuthorize hasAnnotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
      if (hasAnnotation == null || hasAnnotation.withRoles().length == 0) {
        return true;
      }
      String[] requiredRoles = hasAnnotation.withRoles();
      if (hasRequiredRoles(requiredRoles)) {
        return true;
      } else {
        throw new UnauthorizedException("User doesn't have the required access for this API.");
      }
    }

    return true;
  }

  private boolean hasRequiredRoles(String[] requiredRoles) {
    final Set<String> roles = SecurityContext.getRoles();
    if (roles == null || roles.isEmpty()) {
      return false;
    }
    return Arrays.stream(requiredRoles).anyMatch(roles::contains);
  }
}