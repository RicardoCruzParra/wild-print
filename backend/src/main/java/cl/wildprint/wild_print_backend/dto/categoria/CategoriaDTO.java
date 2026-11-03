package cl.wildprint.wild_print_backend.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de una categoría.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    /**
     * Identificador único de la categoría.
     */
    private UUID id;

    /**
     * Nombre de la categoría.
     */
    private String nombre;

    /**
     * Identificador legible utilizado en las URL.
     */
    private String slug;

    /**
     * Descripción de la categoría.
     */
    private String descripcion;

    /**
     * Fecha de creación de la categoría.
     */
    private OffsetDateTime fechaCreacion;

    /**
     * Fecha de la última actualización de la categoría.
     */
    private OffsetDateTime fechaActualizacion;
}