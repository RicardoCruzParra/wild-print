package cl.wildprint.wild_print_backend.dto.producto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

/**
 * DTO que contiene los datos necesarios para crear un producto.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearProductoDTO {

    /**
     * Nombre del producto.
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombre;

    /**
     * Identificador legible utilizado en las URL.
     */
    @NotBlank(message = "El slug del producto es obligatorio")
    @Size(max = 180, message = "El slug no puede superar los 180 caracteres")
    private String slug;

    /**
     * Descripción breve del producto.
     */
    @NotBlank(message = "La descripción corta es obligatoria")
    @Size(max = 300, message = "La descripción corta no puede superar los 300 caracteres")
    private String descripcionCorta;

    /**
     * Descripción completa del producto.
     */
    private String descripcion;

    /**
     * Indica si el producto aparecerá destacado en el catálogo.
     */
    private Boolean destacado;

    /**
     * Identificadores de las categorías asociadas al producto.
     */
    private Set<UUID> categorias;
}