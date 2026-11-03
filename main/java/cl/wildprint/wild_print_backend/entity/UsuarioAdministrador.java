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
 * Representa una cuenta de usuario con acceso
 * al panel administrativo de Wild Print.
 * Almacena los datos de identificación y el hash
 * de la contraseña del administrador.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios_administradores")
public class UsuarioAdministrador {

    /** Identificador único del administrador. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Nombre de usuario utilizado para iniciar sesión. */
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 100)
    private String nombreUsuario;

    /** Correo electrónico del administrador. */
    @Column(name = "correo", nullable = false, unique = true, length = 255)
    private String correo;

    /** Hash de la contraseña. Nunca almacena la contraseña original. */
    @Column(name = "clave_hash", nullable = false, length = 255)
    private String claveHash;

    /** Indica si la cuenta está habilitada. */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /** Fecha y hora de creación de la cuenta. */
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    /** Fecha y hora de la última actualización. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

}