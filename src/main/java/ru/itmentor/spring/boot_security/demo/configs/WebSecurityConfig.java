package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/admin", "/admin/**").access("hasRole(ADMIN)")
                    .antMatchers("/user").hasAnyRole("USER", "ADMIN")
//                    .antMatchers("/login", "/registration", "/error").permitAll()
                    .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().successHandler(successUserHandler)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}