package cl.wildprint.wild_print_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa una imagen asociada a un producto.
 * Almacena los metadatos de la imagen y la clave que permite
 * localizar el archivo en Cloudflare R2.
 * El archivo físico no se almacena en PostgreSQL.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "imagenes_producto")
public class ImagenProducto {

    /** Identificador único de la imagen. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Producto al que pertenece la imagen. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /** Clave del archivo almacenado en Cloudflare R2. */
    @Column(name = "clave_archivo", nullable = false, length = 500)
    private String claveArchivo;

    /** Nombre original del archivo cargado. */
    @Column(name = "nombre_original", nullable = false, length = 255)
    private String nombreOriginal;

    /** Tipo de contenido MIME de la imagen. */
    @Column(name = "tipo_contenido", nullable = false, length = 100)
    private String tipoContenido;

    /** Tamaño del archivo expresado en bytes. */
    @Column(name = "tamano_bytes", nullable = false)
    private Long tamanoBytes;

    /** Texto alternativo para accesibilidad. */
    @Column(name = "texto_alternativo", length = 250)
    private String textoAlternativo;

    /** Posición de la imagen dentro de la galería del producto. */
    @Column(name = "orden", nullable = false)
    private Integer orden = 0;

    /** Indica si la imagen es la portada del producto. */
    @Column(name = "es_portada", nullable = false)
    private boolean esPortada = false;

    /** Fecha y hora de carga de la imagen. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

}