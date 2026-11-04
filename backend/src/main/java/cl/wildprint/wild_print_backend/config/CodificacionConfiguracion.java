package cl.wildprint.wild_print_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura los mecanismos de codificación de contraseñas.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Configuration
public class CodificacionConfiguracion {

    /**
     * Proporciona el codificador de contraseñas BCrypt.
     *
     * @return instancia de PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
