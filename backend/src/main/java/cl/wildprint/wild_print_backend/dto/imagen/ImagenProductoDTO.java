package cl.wildprint.wild_print_backend.dto.imagen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO que representa la información de una imagen asociada a un producto.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoDTO {

    /**
     * Identificador único de la imagen.
     */
    private UUID id;

    /**
     * Identificador del producto al que pertenece la imagen.
     */
    private UUID productoId;

    /**
     * Nombre original del archivo.
     */
    private String nombreOriginal;

    /**
     * Tipo de contenido del archivo.
     */
    private String tipoContenido;

    /**
     * Tamaño del archivo expresado en bytes.
     */
    private Long tamanoBytes;

    /**
     * Texto alternativo utilizado para accesibilidad.
     */
    private String textoAlternativo;

    /**
     * Posición de la imagen dentro de la galería del producto.
     */
    private Integer orden;

    /**
     * Indica si la imagen corresponde a la portada del producto.
     */
    private Boolean esPortada;

    /**
     * URL para acceder a la imagen almacenada.
     */
    private String url;

    /**
     * Fecha de creación del registro de imagen.
     */
    private OffsetDateTime fechaCreacion;
}