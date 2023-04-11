package com.bolsadeideas.springboot.app;

import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailService;

/*    @Autowired
    private DataSource dataSource;*/

    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale", "/api/clientes/**")
                .permitAll()
                /*.requestMatchers("/ver/**").hasAnyRole("USER")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/form/**").hasAnyRole("ADMIN")
                .requestMatchers("/delete/**").hasAnyRole("ADMIN")
                .requestMatchers("/factura/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_404")
                .and().build();
    }

    /*
    //pruebas en memoria
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User
                        .withUsername("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER","ADMIN")
                        .build());
        manager.createUser(
                User
                        .withUsername("user")
                        .password(passwordEncoder.encode("user"))
                        .roles("USER")
                        .build());
        return manager;
    }*/
/*    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                //.userDetailsService(userDetailsService())
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enable from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
                .and().build();
    }*/
}
