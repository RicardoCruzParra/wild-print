package cl.wildprint.wild_print_backend.dto.enlace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de un enlace externo de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnlaceVentaDTO {

    /**
     * Identificador único del enlace.
     */
    private UUID id;

    /**
     * Identificador del producto asociado al enlace.
     */
    private UUID productoId;

    /**
     * Identificador de la plataforma de venta.
     */
    private UUID plataformaId;

    /**
     * Nombre de la plataforma de venta.
     */
    private String nombrePlataforma;

    /**
     * Dirección URL externa para acceder a la plataforma.
     */
    private String urlExterna;

    /**
     * Etiqueta descriptiva que se mostrará al visitante.
     */
    private String etiqueta;

    /**
     * Indica si el enlace se encuentra habilitado.
     */
    private Boolean activo;

    /**
     * Fecha de creación del registro.
     */
    private OffsetDateTime fechaCreacion;

    /**
     * Fecha de la última actualización del registro.
     */
    private OffsetDateTime fechaActualizacion;
}