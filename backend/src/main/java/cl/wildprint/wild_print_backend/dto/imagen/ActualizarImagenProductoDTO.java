package cl.wildprint.wild_print_backend.dto.imagen;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos necesarios para actualizar los metadatos
 * de una imagen asociada a un producto.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarImagenProductoDTO {

    /**
     * Texto alternativo utilizado para accesibilidad.
     */
    @Size(max = 255, message = "El texto alternativo no puede superar los 255 caracteres")
    private String textoAlternativo;

    /**
     * Posición de la imagen dentro de la galería del producto.
     */
    @NotNull(message = "El orden de la imagen es obligatorio")
    @PositiveOrZero(message = "El orden debe ser igual o mayor que cero")
    private Integer orden;

    /**
     * Indica si la imagen corresponde a la portada del producto.
     */
    @NotNull(message = "Debe indicar si la imagen será la portada")
    private Boolean esPortada;
}