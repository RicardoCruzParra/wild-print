package cl.wildprint.wild_print_backend.dto.imagen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO que contiene los datos necesarios para registrar una imagen de producto.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearImagenProductoDTO {

    /**
     * Identificador del producto al que pertenece la imagen.
     */
    @NotNull(message = "El identificador del producto es obligatorio")
    private UUID productoId;

    /**
     * Nombre original del archivo.
     */
    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no puede superar los 255 caracteres")
    private String nombreOriginal;

    /**
     * Tipo de contenido del archivo.
     */
    @NotBlank(message = "El tipo de contenido es obligatorio")
    @Size(max = 100, message = "El tipo de contenido no puede superar los 100 caracteres")
    private String tipoContenido;

    /**
     * Tamaño del archivo expresado en bytes.
     */
    @NotNull(message = "El tamaño del archivo es obligatorio")
    @Positive(message = "El tamaño del archivo debe ser mayor que cero")
    private Long tamanoBytes;

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