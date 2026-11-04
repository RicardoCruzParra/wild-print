package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.dto.autenticacion.IniciarSesionDTO;
import cl.wildprint.wild_print_backend.dto.autenticacion.SesionDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Define las operaciones de autenticación y gestión de sesiones.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface AutenticacionServicio {

    /**
     * Auténtica a un administrador e inicia su sesión.
     *
     * @param dto credenciales de acceso
     * @param solicitud solicitud HTTP
     * @param respuesta respuesta HTTP
     * @return información del administrador autenticado
     */
    SesionDTO iniciarSesion(
            IniciarSesionDTO dto,
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    );

    /**
     * Cierra la sesión del administrador autenticado.
     *
     * @param solicitud solicitud HTTP
     * @param respuesta respuesta HTTP
     */
    void cerrarSesion(
            HttpServletRequest solicitud,
            HttpServletResponse respuesta
    );
}
