package com.weCode.bookStore.config;

import com.weCode.bookStore.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfigurer(UserDetailService userDetailService, PasswordEncoder passwordEncoder, JwtRequestFilter jwtRequestFilter) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // Service where we load user from DB
    private AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);

        // Define Password encryption
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable csrf : https://creativegroundtech.com/what-is-cross-site-request-forgery-csrf/
        // Enable Login endpoint & any requests need to be authenticated
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/api/v1/login").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
