package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.categoria.ActualizarCategoriaDTO;
import cl.wildprint.wild_print_backend.dto.categoria.CategoriaDTO;
import cl.wildprint.wild_print_backend.dto.categoria.CrearCategoriaDTO;
import cl.wildprint.wild_print_backend.entity.Categoria;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entidades Categoria y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
public class CategoriaMapper {

    /**
     * Convierte una entidad Categoria en su representación DTO.
     *
     * @param categoria entidad que se desea convertir
     * @return DTO con la información de la categoría
     */
    public CategoriaDTO aDTO(Categoria categoria) {

        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .slug(categoria.getSlug())
                .descripcion(categoria.getDescripcion())
                .fechaCreacion(categoria.getFechaCreacion())
                .fechaActualizacion(categoria.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad Categoria.
     *
     * @param dto datos recibidos para crear la categoría
     * @return nueva entidad Categoria
     */
    public Categoria desdeCrearDTO(CrearCategoriaDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNombre(dto.getNombre());
        categoria.setSlug(dto.getSlug());
        categoria.setDescripcion(dto.getDescripcion());

        return categoria;
    }

    /**
     * Actualiza los datos de una entidad Categoria existente.
     *
     * @param dto datos recibidos para actualizar la categoría
     * @param categoria entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarCategoriaDTO dto,
            Categoria categoria
    ) {

        categoria.setNombre(dto.getNombre());
        categoria.setSlug(dto.getSlug());
        categoria.setDescripcion(dto.getDescripcion());
    }
}