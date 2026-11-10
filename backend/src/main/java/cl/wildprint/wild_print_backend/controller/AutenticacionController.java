package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.autenticacion.IniciarSesionDTO;
import cl.wildprint.wild_print_backend.dto.autenticacion.SesionDTO;
import cl.wildprint.wild_print_backend.service.AutenticacionServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Expone los endpoints de autenticación de administradores.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/autenticacion")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionServicio autenticacionServicio;

    /**
     * Inicia la sesión de un administrador.
     *
     * @param dto credenciales de acceso
     * @param solicitud solicitud HTTP
     * @param respuesta respuesta HTTP
     * @return información del administrador autenticado
     */
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<SesionDTO> iniciarSesion(
            @Valid @RequestBody IniciarSesionDTO dto,
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    ) {

        SesionDTO sesion = autenticacionServicio.iniciarSesion(
                dto,
                solicitud,
                respuesta
        );

        return ResponseEntity.ok(sesion);
    }

    /**
     * Cierra la sesión del administrador autenticado.
     *
     * @param solicitud solicitud HTTP
     * @param respuesta respuesta HTTP
     * @return respuesta sin contenido
     */
    @PostMapping("/cerrar-sesion")
    public ResponseEntity<Void> cerrarSesion(
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    ) {

        autenticacionServicio.cerrarSesion(solicitud, respuesta);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene el token CSRF necesario para las peticiones protegidas.
     *
     * @param csrfToken token de protección CSRF
     * @return token CSRF generado para la sesión
     */
    @GetMapping("/csrf")
    public ResponseEntity<CsrfToken> obtenerTokenCSRF(
            CsrfToken csrfToken
    ) {
        return ResponseEntity.ok(csrfToken);
    }

    /**
     * Obtiene la información del administrador autenticado
     * en la sesión actual.
     *
     * @return información de la sesión o 401 si no hay sesión activa
     */
    @GetMapping("/sesion")
    public ResponseEntity<SesionDTO> obtenerSesionActual() {

        return autenticacionServicio.obtenerSesionActual()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
