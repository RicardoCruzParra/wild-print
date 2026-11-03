package cl.wildprint.wild_print_backend.dto.producto;

import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * DTO que representa la información pública y administrativa de un producto.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    /**
     * Identificador único del producto.
     */
    private UUID id;

    /**
     * Nombre del producto.
     */
    private String nombre;

    /**
     * Identificador legible utilizado en las URL.
     */
    private String slug;

    /**
     * Descripción breve del producto.
     */
    private String descripcionCorta;

    /**
     * Descripción completa del producto.
     */
    private String descripcion;

    /**
     * Estado de publicación del producto.
     */
    private EstadoProducto estado;

    /**
     * Indica si el producto aparece destacado en el catálogo.
     */
    private Boolean destacado;

    /**
     * Identificadores de las categorías asociadas al producto.
     */
    private Set<UUID> categorias;

    /**
     * Fecha de creación del producto.
     */
    private OffsetDateTime fechaCreacion;

    /**
     * Fecha de la última actualización del producto.
     */
    private OffsetDateTime fechaActualizacion;
}