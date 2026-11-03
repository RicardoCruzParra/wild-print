package cl.wildprint.wild_print_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa una configuración dinámica del sitio web.
 * Permite almacenar valores configurables mediante
 * una clave única, evitando modificar código para
 * cambiar determinados parámetros del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "configuracion_sitio")
public class ConfiguracionSitio {

    /** Identificador único de la configuración. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Clave única que identifica el parámetro. */
    @Column(name = "clave", nullable = false, unique = true, length = 100)
    private String clave;

    /** Valor asociado a la configuración. */
    @Column(name = "valor", columnDefinition = "TEXT")
    private String valor;

    /** Fecha y hora de la última actualización. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

}