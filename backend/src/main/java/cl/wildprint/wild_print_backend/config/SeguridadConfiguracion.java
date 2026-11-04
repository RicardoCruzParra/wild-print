package cl.wildprint.wild_print_backend.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configura las reglas de seguridad y autenticación de la aplicación.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SeguridadConfiguracion {

    /**
     * Configura las reglas de acceso a los endpoints.
     *
     * @param http configuración de seguridad HTTP
     * @return cadena de filtros de seguridad
     * @throws Exception si ocurre un error de configuración
     */
    @Bean
    public SecurityFilterChain seguridadFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse()
                        )
                        .csrfTokenRequestHandler(
                                new CsrfTokenRequestAttributeHandler()
                        )
                )
                .cors(cors -> {})
                .authorizeHttpRequests(autorizacion -> autorizacion
                        .requestMatchers(
                                "/api/autenticacion/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos",
                                "/api/productos/slug/**",
                                "/api/categorias",
                                "/api/categorias/slug/**",
                                "/api/plataformas-venta/activas",
                                "/api/productos/*/imagenes",
                                "/api/productos/*/enlaces-venta"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(sesion -> sesion
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * Expone el administrador de autenticación de Spring Security.
     *
     * @param configuracion configuración de autenticación
     * @return administrador de autenticación
     * @throws Exception si ocurre un error al obtenerlo
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuracion
    ) throws Exception {
        return configuracion.getAuthenticationManager();
    }

    /**
     * Configura los permisos de comunicación entre React y Spring Boot.
     *
     * @return configuración CORS de la aplicación
     */
    @Bean
    public CorsConfigurationSource configuracionCORS() {

        CorsConfiguration configuracion = new CorsConfiguration();

        configuracion.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuracion.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        configuracion.setAllowedHeaders(
                List.of("Content-Type", "X-XSRF-TOKEN")
        );

        configuracion.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource origen =
                new UrlBasedCorsConfigurationSource();

        origen.registerCorsConfiguration("/**", configuracion);

        return origen;
    }
}
