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
 * Representa una categoría utilizada para clasificar
 * los productos del catálogo de Wild Print.
 * Una categoría puede estar asociada a múltiples productos
 * y un producto puede pertenecer a múltiples categorías.
 * Las categorías son permanentes y no contemplan
 * eliminación ni desactivación.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    /** Identificador único de la categoría. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Nombre visible de la categoría. */
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    /** Identificador textual utilizado en su URL. */
    @Column(name = "slug", nullable = false, unique = true, length = 120)
    private String slug;

    /** Descripción opcional de la categoría. */
    @Column(name = "descripcion", length = 300)
    private String descripcion;

    /** Fecha y hora de creación de la categoría. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    /** Fecha y hora de actualizacion de la categoría. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

    /**
     * Productos asociados a esta categoría.
     * Representa el lado inverso de la relación muchos a mucho definido
     *  en Producto mediante la tabla productos_categorias.
     */
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

}