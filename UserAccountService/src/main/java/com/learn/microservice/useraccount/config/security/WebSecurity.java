package com.learn.microservice.useraccount.config.security;

import com.learn.microservice.useraccount.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final Environment env;

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.env = env;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        var authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

        var authenticationManager = authenticationManagerBuilder.build();
        var authenticationFilter = new AuthenticationFilter(authenticationManager, userService, env);
        authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));

        http
            .csrf(AbstractHttpConfigurer::disable) // Non-deprecated CSRF configuration
            .authorizeHttpRequests(auth -> auth // Non-deprecated authorization configuration
                    .requestMatchers("/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('" + env.getProperty("gateway.ip") + "')"))
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    .anyRequest().authenticated()
            )
            .addFilter(authenticationFilter)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

//    @Bean
//    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//        http.authorizeHttpRequests()
//                .requestMatchers("/users/**").permitAll()
//                .requestMatchers("/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('"+ env.getProperty("gateway.ip") +"')"))
//                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.headers().frameOptions().disable();
//
//        return http.build();
//    }
}
