package cl.wildprint.wild_print_backend.dto.administrador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de un administrador.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDTO {

    /**
     * Identificador único del administrador.
     */
    private UUID id;

    /**
     * Nombre de usuario del administrador.
     */
    private String nombreUsuario;

    /**
     * Correo electrónico del administrador.
     */
    private String correo;

    /**
     * Indica si la cuenta del administrador se encuentra habilitada.
     */
    private Boolean activo;

    /**
     * Fecha de creación de la cuenta.
     */
    private OffsetDateTime fechaCreacion;

    /**
     * Fecha de la última actualización de la cuenta.
     */
    private OffsetDateTime fechaActualizacion;
}