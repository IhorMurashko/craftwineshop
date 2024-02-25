package com.craftWine.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final MyUserServiceDetails userService;
    private final MyTokenFilter tokenFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(MyUserServiceDetails userService, MyTokenFilter tokenFilter,
                          JwtAuthEntryPoint jwtAuthEntryPoint, MyAccessDeniedHandler myAccessDeniedHandler,
                          BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.tokenFilter = tokenFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(jwtAuthEntryPoint).accessDeniedHandler(myAccessDeniedHandler));
        http.authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/api/v1/user_cart/**").hasRole("USER")
                                .requestMatchers("/api/v1/user_favorite/**").hasRole("USER")
                                .requestMatchers("/api/v1/comments/**").hasRole("USER")
                                .requestMatchers("/api/v1/evaluation/**").hasRole("USER")
//                                .requestMatchers("/reg/login").permitAll()
//                                .requestMatchers("reg/register").permitAll()
////                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
////                                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
//                                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "USER")
//                                .requestMatchers("/user/**").hasAuthority("USER")
//                                .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().permitAll()
//                        .anyRequest().authenticated()

        );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
