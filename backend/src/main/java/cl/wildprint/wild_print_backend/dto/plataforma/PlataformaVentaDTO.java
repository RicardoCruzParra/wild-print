package cl.wildprint.wild_print_backend.dto.plataforma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de una plataforma de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlataformaVentaDTO {

    /**
     * Identificador único de la plataforma.
     */
    private UUID id;

    /**
     * Código interno utilizado para identificar la plataforma.
     */
    private String codigo;

    /**
     * Nombre visible de la plataforma.
     */
    private String nombre;

    /**
     * Indica si la plataforma se encuentra habilitada.
     */
    private Boolean activa;

    /**
     * Fecha de creación del registro.
     */
    private OffsetDateTime fechaCreacion;
}