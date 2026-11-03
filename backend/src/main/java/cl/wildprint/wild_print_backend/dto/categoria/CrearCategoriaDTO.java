package cl.wildprint.wild_print_backend.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos necesarios para crear una categoría.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearCategoriaDTO {

    /**
     * Nombre de la categoría.
     */
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    /**
     * Identificador legible utilizado en las URL.
     */
    @NotBlank(message = "El slug de la categoría es obligatorio")
    @Size(max = 120, message = "El slug no puede superar los 120 caracteres")
    private String slug;

    /**
     * Descripción de la categoría.
     */
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;
}