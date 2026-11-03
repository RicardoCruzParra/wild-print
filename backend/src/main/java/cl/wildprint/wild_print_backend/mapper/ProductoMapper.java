package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.producto.ActualizarProductoDTO;
import cl.wildprint.wild_print_backend.dto.producto.CrearProductoDTO;
import cl.wildprint.wild_print_backend.dto.producto.ProductoDTO;
import cl.wildprint.wild_print_backend.entity.Categoria;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.repository.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper encargado de convertir entidades Producto y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final CategoriaRepositorio categoriaRepositorio;

    /**
     * Convierte una entidad Producto en su representación DTO.
     *
     * @param producto entidad que se desea convertir
     * @return DTO con la información del producto
     */
    public ProductoDTO aDTO(Producto producto) {

        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .slug(producto.getSlug())
                .descripcionCorta(producto.getDescripcionCorta())
                .descripcion(producto.getDescripcion())
                .estado(producto.getEstado())
                .destacado(producto.isDestacado())
                .categorias(producto.getCategorias().stream()
                        .map(Categoria::getId)
                        .collect(Collectors.toSet()))
                .fechaCreacion(producto.getFechaCreacion())
                .fechaActualizacion(producto.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad Producto.
     *
     * @param dto datos recibidos para crear el producto
     * @return nueva entidad Producto
     * @throws IllegalArgumentException si alguna categoría no existe
     */
    public Producto desdeCrearDTO(CrearProductoDTO dto) {

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setSlug(dto.getSlug());
        producto.setDescripcionCorta(dto.getDescripcionCorta());
        producto.setDescripcion(dto.getDescripcion());
        producto.setDestacado(dto.getDestacado() != null && dto.getDestacado());

        producto.setCategorias(obtenerCategorias(dto.getCategorias()));

        return producto;
    }

    /**
     * Actualiza los datos de una entidad Producto existente.
     *
     * @param dto datos recibidos para actualizar el producto
     * @param producto entidad que se desea modificar
     * @throws IllegalArgumentException si alguna categoría no existe
     */
    public void actualizarDesdeDTO(ActualizarProductoDTO dto, Producto producto) {

        producto.setNombre(dto.getNombre());
        producto.setSlug(dto.getSlug());
        producto.setDescripcionCorta(dto.getDescripcionCorta());
        producto.setDescripcion(dto.getDescripcion());
        producto.setEstado(dto.getEstado());
        producto.setDestacado(dto.getDestacado());

        producto.setCategorias(obtenerCategorias(dto.getCategorias()));
    }

    /**
     * Obtiene las entidades Categoria correspondientes a los identificadores recibidos.
     *
     * @param identificadores identificadores de las categorías
     * @return conjunto de categorías encontradas
     * @throws IllegalArgumentException si alguna categoría no existe
     */
    private Set<Categoria> obtenerCategorias(Set<UUID> identificadores) {

        if (identificadores == null || identificadores.isEmpty()) {
            return new HashSet<>();
        }

        Set<Categoria> categorias = new HashSet<>(
                categoriaRepositorio.findAllById(identificadores)
        );

        if (categorias.size() != identificadores.size()) {
            throw new IllegalArgumentException(
                    "Una o más categorías indicadas no existen"
            );
        }

        return categorias;
    }
}