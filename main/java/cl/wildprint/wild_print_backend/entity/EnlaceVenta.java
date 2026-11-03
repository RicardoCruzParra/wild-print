package cl.wildprint.wild_print_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa un enlace externo de comercialización
 * asociado a un producto.
 * Permite registrar uno o varios enlaces por producto,
 * incluso cuando pertenecen a una misma plataforma.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enlaces_venta")
public class EnlaceVenta {

    /** Identificador único del enlace. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Producto asociado al enlace. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /** Plataforma externa a la que pertenece el enlace. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plataforma_id", nullable = false)
    private PlataformaVenta plataforma;

    /** Dirección URL externa de comercialización. */
    @Column(name = "url_externa", nullable = false, length = 1000)
    private String urlExterna;

    /** Texto opcional que identifica el enlace. */
    @Column(name = "etiqueta", length = 100)
    private String etiqueta;

    /** Indica si el enlace está disponible públicamente. */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /** Fecha y hora de creación del registro. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    /** Fecha y hora de la última actualización. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

}