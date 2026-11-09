package cl.wildprint.wild_print_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Representa un producto del catálogo de Wild Print.
 * Contiene la información descriptiva, estado de publicación,
 * categorías asociadas y fechas de control del producto.
 * Los productos pueden encontrarse en estado BORRADOR,
 * PUBLICADO u OCULTO. También contempla eliminación lógica
 * mediante el campo fechaEliminacion.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    /** Identificador único del producto. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Nombre público del producto. */
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    /** Identificador textual utilizado en la URL del producto. */
    @Column(name = "slug", nullable = false, unique = true, length = 180)
    private String slug;

    /** Descripción breve utilizada en las vistas del catálogo. */
    @Column(name = "descripcion_corta", length = 300)
    private String descripcionCorta;

    /** Descripción completa del producto. */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Estado actual de publicación del producto. */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoProducto estado = EstadoProducto.BORRADOR;

    /** Indica si el producto debe destacarse en el catálogo. */
    @Column(name = "destacado", nullable = false)
    private boolean destacado = false;

    /** Fecha y hora de creación del producto. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    /** Fecha y hora de la última actualización. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

    /** Fecha de eliminación lógica. NULL indica que no está en papelera. */
    @Column(name = "fecha_eliminacion")
    private OffsetDateTime fechaEliminacion;

    /**
     * Categorías a las que pertenece el producto.
     * Relación muchos a muchos: un producto puede pertenecer
     * a varias categorías y una categoría puede contener
     * varios productos.
     * La relación se administra mediante la tabla
     * productos_categorias.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "productos_categorias",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    /**
     * Imágenes asociadas al producto.
     * Un producto puede tener varias imágenes, incluyendo
     * una imagen principal y otras secundarias.
     */
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<ImagenProducto> imagenes = new HashSet<>();
}