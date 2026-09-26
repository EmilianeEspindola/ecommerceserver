package br.edu.utfpr.pb.pw44s.ecommerceserver.security;

import br.edu.utfpr.pb.pw44s.ecommerceserver.service.AuthService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurity {
    private final AuthService authService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurity(AuthService authService, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authService = authService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(authService).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // Necessário para o console do H2
        http.headers(headers -> headers.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::disable));

        // Desabilita CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Configuração CORS
        http.cors(cors -> corsConfigurationSource());

        // Define o objeto responsável pelo tratamento de exceção ao entrar com credenciais inválidas.
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        // Define quais endpoints são públicos
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll() // Cadastro de usuário
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll() // Consulta de produtos
                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll() // Consulta de categorias
                .requestMatchers("/h2-console/**").permitAll() // Console H2
                .requestMatchers("/error/**").permitAll() // Tratamento de erros
                .anyRequest().authenticated()); // Demais endpoints exigem autenticação

        http.authenticationManager(authenticationManager)
                // Login e geração do JWT
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authService))
                // Verificação do JWT
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authService))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Não mantém sessão no servidor.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}