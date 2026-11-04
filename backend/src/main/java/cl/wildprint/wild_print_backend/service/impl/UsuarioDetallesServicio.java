package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import cl.wildprint.wild_print_backend.service.UsuarioAdministradorServicio;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.jspecify.annotations.NonNull;

/**
 * Permite cargar los datos de autenticación de los administradores.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetallesServicio implements UserDetailsService {

    private final UsuarioAdministradorServicio usuarioAdministradorServicio;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String nombreUsuario)
            throws UsernameNotFoundException {

        UsuarioAdministrador administrador =
                usuarioAdministradorServicio.buscarPorNombreUsuario(nombreUsuario)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "No existe el administrador indicado"
                        ));

        return User.withUsername(administrador.getNombreUsuario())
                .password(administrador.getClaveHash())
                .roles("ADMIN")
                .disabled(!administrador.isActivo())
                .build();
    }
}
