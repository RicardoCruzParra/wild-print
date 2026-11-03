package cl.wildprint.wild_print_backend.dto.autenticacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO que representa la información de una sesión autenticada.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionDTO {

    /**
     * Identificador único del administrador autenticado.
     */
    private UUID administradorId;

    /**
     * Nombre de usuario del administrador autenticado.
     */
    private String nombreUsuario;

    /**
     * Correo electrónico del administrador autenticado.
     */
    private String correo;
}