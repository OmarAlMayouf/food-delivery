package io.github.omaralmayouf.food_delivery.shared.config;

import lombok.experimental.FieldDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SecurityConfiguration {

    static final String[] WHITELIST_URLS = {
            "/restaurants/**",

            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",

            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(WHITELIST_URLS).permitAll()
                                .anyRequest().authenticated()
                )
                .build();

    }

}
