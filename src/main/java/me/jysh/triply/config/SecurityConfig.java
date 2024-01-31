package me.jysh.triply.config;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.interceptors.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig implements WebMvcConfigurer {

    final AuthenticationInterceptor authNInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authNInterceptor)
                .excludePathPatterns("/auth/**");
    }
}
