package ru.letsdigit.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/**").permitAll()
                        // 3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
                        .requestMatchers("/ui/issue/**").hasAuthority("admin")
                        /* 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
                         !! добавлен админ к заданию */
                        .requestMatchers("/ui/reader/**").hasAnyAuthority("reader", "admin")
                        // 3.5* Ресурсы книг (books) доступны всем авторизованным пользователям
                        .requestMatchers("/ui/book/**").authenticated()
                        .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/api/v1/**"));
        return http.build();
    }
}
