package cl.wildprint.wild_print_backend.dto.configuracion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de una configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionSitioDTO {

    /**
     * Identificador único de la configuración.
     */
    private UUID id;

    /**
     * Clave utilizada para identificar la configuración.
     */
    private String clave;

    /**
     * Valor asociado a la configuración.
     */
    private String valor;

    /**
     * Fecha de la última actualización de la configuración.
     */
    private OffsetDateTime fechaActualizacion;
}