package com.bolsadeideas.springboot.app;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER").build());

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN", "USER").build());

        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
/*                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
                .requestMatchers("/ver/**").hasAnyRole("USER")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/form/**").hasAnyRole("ADMIN")
                .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .requestMatchers("/factura/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");

        return http.build();
    }

}
