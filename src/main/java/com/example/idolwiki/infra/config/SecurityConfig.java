package com.example.idolwiki.infra.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

/**
 * CORS 문제를 해결하는 서버 설정
 */

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.addAllowedOrigin ("http://localhost:8080"); // |  요청이 들어오는 서버를 저장한다
            corsConfig.setAllowedHeaders (List.of("Authorization", "Content-Type"));
            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
            corsConfig.setAllowCredentials(true);
            corsConfig.setMaxAge (3600L); // 1시간

            UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration( "/**", corsConfig); // 모든 요청에 cors 설정을 적용한다

            return source;
        }
/**
 *다양한 요청에 대해 알맞은 권한을 부여하는 것을 목표로 할 것. *권한이 없는 경우 401(Unauthorized) 코드가 나타남.
 */

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/common/**").permitAll()
                .requestMatchers("/admin/**").hasAnyRole("admin") ///admin으로 들어오는 요청은 admin 권한을 가졌을 때만 허용
        );


        http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        http.cors(configurer -> configurer.configurationSource(this.corsConfigurationSource()));
        return http.build();
    }


}
