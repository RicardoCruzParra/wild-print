package cl.wildprint.wild_print_backend.dto.enlace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO que contiene los datos necesarios para crear un enlace externo de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEnlaceVentaDTO {

    /**
     * Identificador del producto asociado al enlace.
     */
    @NotNull(message = "El identificador del producto es obligatorio")
    private UUID productoId;

    /**
     * Identificador de la plataforma de venta.
     */
    @NotNull(message = "El identificador de la plataforma es obligatorio")
    private UUID plataformaId;

    /**
     * Dirección URL externa para acceder a la plataforma.
     */
    @NotBlank(message = "La URL externa es obligatoria")
    @Size(max = 2048, message = "La URL no puede superar los 2048 caracteres")
    @Pattern(
            regexp = "^https://.+$",
            message = "La URL debe utilizar el protocolo HTTPS"
    )
    private String urlExterna;

    /**
     * Etiqueta descriptiva que se mostrará al visitante.
     */
    @Size(max = 100, message = "La etiqueta no puede superar los 100 caracteres")
    private String etiqueta;
}