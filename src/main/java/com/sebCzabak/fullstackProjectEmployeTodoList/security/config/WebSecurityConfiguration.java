package com.sebCzabak.fullstackProjectEmployeTodoList.security.config;

import com.sebCzabak.fullstackProjectEmployeTodoList.service.EmployeeService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    public WebSecurityConfiguration(EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{


        httpSecurity
                .csrf().disable()      .authorizeHttpRequests()
                .requestMatchers("/api/AdminPage")
                .hasRole("ADMIN")
                .requestMatchers("/api/**")
                .permitAll()
                .requestMatchers("/api/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                //.loginPage("http://localhost:3000/login")
                //.loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("http://localhost:3000")
                .failureUrl("/http://localhost:3000:/home")

               // .deleteCookies("JSESSIONID")
                .and()
                .logout().permitAll()
                .and()
                .httpBasic();

        return httpSecurity.build();
//                    .requestMatchers("/api/employees/**")
//                    .permitAll()
//                .requestMatchers("/api/tasks/**")
//                .permitAll()
    }

    protected void configure(AuthenticationManager authenticationManager)throws Exception{
        authenticationManager.authenticate((Authentication) daoAuthenticationProvider());
    }

    @Bean
     public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(employeeService);
        return provider;

    }
}
