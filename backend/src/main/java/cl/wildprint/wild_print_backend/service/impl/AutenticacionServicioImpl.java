package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.dto.autenticacion.IniciarSesionDTO;
import cl.wildprint.wild_print_backend.dto.autenticacion.SesionDTO;
import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import cl.wildprint.wild_print_backend.service.AutenticacionServicio;
import cl.wildprint.wild_print_backend.service.UsuarioAdministradorServicio;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementa las operaciones de autenticación y gestión de sesiones.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacionServicioImpl implements AutenticacionServicio {

    private final AuthenticationManager authenticationManager;
    private final UsuarioAdministradorServicio usuarioAdministradorServicio;

    private final HttpSessionSecurityContextRepository repositorioContexto =
            new HttpSessionSecurityContextRepository();

    /**
     * {@inheritDoc}
     */
    @Override
    public SesionDTO iniciarSesion(
            IniciarSesionDTO dto,
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    ) {

        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getNombreUsuario(),
                        dto.getClave()
                )
        );

        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(autenticacion);
        SecurityContextHolder.setContext(contexto);

        repositorioContexto.saveContext(contexto, solicitud, respuesta);

        UsuarioAdministrador administrador =
                usuarioAdministradorServicio.buscarPorNombreUsuario(
                        autenticacion.getName()
                ).orElseThrow(() -> new IllegalArgumentException(
                        "No existe el usuario administrador"
                ));

        return SesionDTO.builder()
                .administradorId(administrador.getId())
                .nombreUsuario(administrador.getNombreUsuario())
                .correo(administrador.getCorreo())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cerrarSesion(
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    ) {

        Authentication autenticacion =
                SecurityContextHolder.getContext().getAuthentication();

        new SecurityContextLogoutHandler().logout(
                solicitud,
                respuesta,
                autenticacion
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SesionDTO> obtenerSesionActual() {

        Authentication autenticacion =
                SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion == null
                || !autenticacion.isAuthenticated()
                || autenticacion instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return usuarioAdministradorServicio
                .buscarPorNombreUsuario(autenticacion.getName())
                .map(administrador -> SesionDTO.builder()
                        .administradorId(administrador.getId())
                        .nombreUsuario(administrador.getNombreUsuario())
                        .correo(administrador.getCorreo())
                        .build());
    }
}
