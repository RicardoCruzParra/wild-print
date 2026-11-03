package cl.wildprint.wild_print_backend.dto.plataforma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos necesarios para actualizar una plataforma de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarPlataformaVentaDTO {

    /**
     * Código interno utilizado para identificar la plataforma.
     */
    @NotBlank(message = "El código de la plataforma es obligatorio")
    @Size(max = 50, message = "El código no puede superar los 50 caracteres")
    @Pattern(
            regexp = "^[A-Z0-9_-]+$",
            message = "El código solo puede contener letras mayúsculas, números, guiones y guiones bajos"
    )
    private String codigo;

    /**
     * Nombre visible de la plataforma.
     */
    @NotBlank(message = "El nombre de la plataforma es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;
}