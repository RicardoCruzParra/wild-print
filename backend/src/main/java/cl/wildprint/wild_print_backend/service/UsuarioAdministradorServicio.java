package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de usuarios administradores.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface UsuarioAdministradorServicio {

    /**
     * Registra un nuevo usuario administrador.
     *
     * @param usuario usuario que se desea registrar
     * @return usuario administrador creado
     */
    UsuarioAdministrador crear(UsuarioAdministrador usuario);

    /**
     * Actualiza los datos de un usuario administrador existente.
     *
     * @param id identificador del usuario
     * @param usuario datos actualizados
     * @return usuario administrador actualizado
     */
    UsuarioAdministrador actualizar(UUID id, UsuarioAdministrador usuario);

    /**
     * Busca un usuario administrador por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado, si existe
     */
    Optional<UsuarioAdministrador> buscarPorId(UUID id);

    /**
     * Busca un usuario administrador por su nombre de usuario.
     *
     * @param nombreUsuario nombre de usuario
     * @return usuario encontrado, si existe
     */
    Optional<UsuarioAdministrador> buscarPorNombreUsuario(String nombreUsuario);

    /**
     * Busca un usuario administrador por su correo electrónico.
     *
     * @param correo correo electrónico
     * @return usuario encontrado, si existe
     */
    Optional<UsuarioAdministrador> buscarPorCorreo(String correo);

    /**
     * Obtiene todos los usuarios administradores registrados.
     *
     * @return lista de usuarios administradores
     */
    List<UsuarioAdministrador> listarTodos();

    /**
     * Cambia el estado de activación de un usuario administrador.
     *
     * @param id identificador del usuario
     * @param activo nuevo estado de activación
     * @return usuario administrador actualizado
     */
    UsuarioAdministrador cambiarEstado(UUID id, boolean activo);
}