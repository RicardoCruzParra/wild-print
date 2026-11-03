package cl.wildprint.wild_print_backend.dto.configuracion;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos necesarios para actualizar una configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarConfiguracionSitioDTO {

    /**
     * Valor asociado a la configuración.
     */
    @NotBlank(message = "El valor de configuración es obligatorio")
    private String valor;
}