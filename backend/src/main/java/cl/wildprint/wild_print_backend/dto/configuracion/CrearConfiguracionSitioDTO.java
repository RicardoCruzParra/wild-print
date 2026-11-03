package cl.wildprint.wild_print_backend.dto.configuracion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos necesarios para crear una configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearConfiguracionSitioDTO {

    /**
     * Clave utilizada para identificar la configuración.
     */
    @NotBlank(message = "La clave de configuración es obligatoria")
    @Size(max = 100, message = "La clave no puede superar los 100 caracteres")
    private String clave;

    /**
     * Valor asociado a la configuración.
     */
    @NotBlank(message = "El valor de configuración es obligatorio")
    private String valor;
}