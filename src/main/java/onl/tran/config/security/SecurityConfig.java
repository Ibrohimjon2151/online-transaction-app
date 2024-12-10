package onl.tran.config.security;

import lombok.RequiredArgsConstructor;
import onl.tran.config.util.token.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;

  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
      .requestMatchers("/api/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-ui.html/**")
      .permitAll()
      .anyRequest().authenticated()
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


}
