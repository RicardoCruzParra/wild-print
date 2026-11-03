package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import cl.wildprint.wild_print_backend.repository.UsuarioAdministradorRepositorio;
import cl.wildprint.wild_print_backend.service.UsuarioAdministradorServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de usuarios administradores.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioAdministradorServicioImpl implements UsuarioAdministradorServicio {

    /**
     * Repositorio encargado del acceso a los usuarios administradores.
     */
    private final UsuarioAdministradorRepositorio usuarioAdministradorRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public UsuarioAdministrador crear(UsuarioAdministrador usuario) {
        return usuarioAdministradorRepositorio.save(usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsuarioAdministrador actualizar(UUID id, UsuarioAdministrador usuario) {
        UsuarioAdministrador usuarioExistente = obtenerUsuario(id);

        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setActivo(usuario.isActivo());

        return usuarioAdministradorRepositorio.save(usuarioExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioAdministrador> buscarPorId(UUID id) {
        return usuarioAdministradorRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioAdministrador> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioAdministradorRepositorio.findByNombreUsuario(nombreUsuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioAdministrador> buscarPorCorreo(String correo) {
        return usuarioAdministradorRepositorio.findByCorreo(correo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioAdministrador> listarTodos() {
        return usuarioAdministradorRepositorio.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsuarioAdministrador cambiarEstado(UUID id, boolean activo) {
        UsuarioAdministrador usuario = obtenerUsuario(id);

        usuario.setActivo(activo);

        return usuarioAdministradorRepositorio.save(usuario);
    }

    /**
     * Obtiene un usuario administrador existente o genera una excepción.
     *
     * @param id identificador del usuario
     * @return usuario administrador encontrado
     * @throws IllegalArgumentException si el usuario no existe
     */
    private UsuarioAdministrador obtenerUsuario(UUID id) {
        return usuarioAdministradorRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el usuario administrador con identificador: " + id));
    }
}