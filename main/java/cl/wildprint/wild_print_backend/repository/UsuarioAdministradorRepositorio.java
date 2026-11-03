package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de usuarios
 * administradores del sistema.
 * Permite consultar y administrar las cuentas que tienen
 * acceso al panel administrativo de Wild Print.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface UsuarioAdministradorRepositorio extends JpaRepository<UsuarioAdministrador, UUID> {
}