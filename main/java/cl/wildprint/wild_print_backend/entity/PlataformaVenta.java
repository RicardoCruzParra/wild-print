package cl.wildprint.wild_print_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa una plataforma externa donde se comercializan
 * los productos de Wild Print.
 * Permite identificar plataformas mediante un código
 * y controlar su disponibilidad para nuevos enlaces.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plataformas_venta")
public class PlataformaVenta {

    /** Identificador único de la plataforma. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Código interno utilizado para identificarla. */
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    /** Nombre visible de la plataforma. */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Indica si la plataforma está disponible para nuevos enlaces. */
    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    /** Fecha y hora de creación del registro. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

}