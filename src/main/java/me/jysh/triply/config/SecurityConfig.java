package me.jysh.triply.config;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.config.interceptors.AuthenticationInterceptor;
import me.jysh.triply.config.interceptors.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up security-related configurations in the application.
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig implements WebMvcConfigurer {

  private final AuthenticationInterceptor authNInterceptor;

  private final AuthorizationInterceptor authZInterceptor;

  /**
   * Adds the {@link AuthenticationInterceptor} to the Spring MVC interceptors with some specific
   * exclusions.
   *
   * @param registry The registry containing the interceptors.
   */
  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(authNInterceptor)
        .excludePathPatterns("/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");

    registry.addInterceptor(authZInterceptor);
  }

  /**
   * Provides a BCryptPasswordEncoder as a Spring Bean for password encoding.
   * <p>
   * This method configures and returns an instance of {@link BCryptPasswordEncoder}, which is a
   * widely-used password hashing algorithm. It is recommended for securing user passwords in a
   * secure and irreversible manner.
   * </p>
   *
   * @return An instance of {@link BCryptPasswordEncoder} for password encoding.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}