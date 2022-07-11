package com.mapto.api.config.security;

import com.mapto.api.app.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String profileActivated;
    private final JwtAuthenticationEntryPoint unAuthorizedHandler;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(WebSecurity web) {
        if(!profileActivated.equalsIgnoreCase("prod")) {
            web.ignoring().antMatchers(
                "/test_db/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            );
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and().csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers().disable().exceptionHandling().authenticationEntryPoint(unAuthorizedHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/api/**/app/auth").permitAll()
            .antMatchers("/api/**/app/auth/email").permitAll()
            .antMatchers("/api/**/app/user/signUp").permitAll()
            .antMatchers("/api/**/app/user/exist").permitAll()
            .antMatchers("/error").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsServiceImpl);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> JwtAuthenticationFilterRegistration(JwtAuthenticationFilter jwtAuthenticationFilter){
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(jwtAuthenticationFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}

