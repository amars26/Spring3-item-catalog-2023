package com.softversko.aplikacija.security;

import com.softversko.aplikacija.model.User;
import com.softversko.aplikacija.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/register","/process_register", "/images/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) -> formLogin
                        .defaultSuccessUrl("/", true) // Specify your desired default success URL
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.findByUsername(username);

                UserDetails userDetails = new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return Collections.singleton(new GrantedAuthority() {
                            @Override
                            public String getAuthority() {
                                return user.getRole();
                            }
                        });
                    }

                    @Override
                    public String getPassword() {
                        return user.getPassword();
                    }

                    @Override
                    public String getUsername() {
                        return user.getUsername();
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
                return userDetails;
            }
        };
    }


}
