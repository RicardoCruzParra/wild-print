package cl.wildprint.wild_print_backend.config;

import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import cl.wildprint.wild_print_backend.repository.UsuarioAdministradorRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Crea los datos mínimos necesarios para desarrollo.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DatosDesarrollo implements CommandLineRunner {

    private final UsuarioAdministradorRepositorio repositorio;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el administrador inicial si no existe ninguno.
     *
     * @param args argumentos de inicio de la aplicación
     */
    @Override
    @Transactional
    public void run(String... args) {

        if (repositorio.count() > 0) {
            return;
        }

        UsuarioAdministrador admin = new UsuarioAdministrador();
        admin.setNombreUsuario("admin");
        admin.setCorreo("admin@wildprint.local");
        admin.setClaveHash(passwordEncoder.encode("admin123"));
        admin.setActivo(true);

        repositorio.save(admin);
    }
}