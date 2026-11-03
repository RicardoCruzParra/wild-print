package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.imagen.ActualizarImagenProductoDTO;
import cl.wildprint.wild_print_backend.dto.imagen.CrearImagenProductoDTO;
import cl.wildprint.wild_print_backend.dto.imagen.ImagenProductoDTO;
import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.repository.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper encargado de convertir entidades ImagenProducto y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class ImagenProductoMapper {

    private final ProductoRepositorio productoRepositorio;

    /**
     * Convierte una entidad ImagenProducto en su representación DTO.
     *
     * @param imagen entidad que se desea convertir
     * @return DTO con la información de la imagen
     */
    public ImagenProductoDTO aDTO(ImagenProducto imagen) {

        return ImagenProductoDTO.builder()
                .id(imagen.getId())
                .productoId(imagen.getProducto().getId())
                .url(null)
                .nombreOriginal(imagen.getNombreOriginal())
                .tipoContenido(imagen.getTipoContenido())
                .tamanoBytes(imagen.getTamanoBytes())
                .textoAlternativo(imagen.getTextoAlternativo())
                .orden(imagen.getOrden())
                .esPortada(imagen.isEsPortada())
                .fechaCreacion(imagen.getFechaCreacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad ImagenProducto.
     *
     * @param dto datos recibidos para crear la imagen
     * @return nueva entidad ImagenProducto
     */
    public ImagenProducto desdeCrearDTO(CrearImagenProductoDTO dto) {

        ImagenProducto imagen = new ImagenProducto();

        imagen.setProducto(obtenerProducto(dto.getProductoId()));
        imagen.setNombreOriginal(dto.getNombreOriginal());
        imagen.setTipoContenido(dto.getTipoContenido());
        imagen.setTamanoBytes(dto.getTamanoBytes());
        imagen.setTextoAlternativo(dto.getTextoAlternativo());
        imagen.setOrden(dto.getOrden());
        imagen.setEsPortada(dto.getEsPortada());

        return imagen;
    }

    /**
     * Actualiza los datos de una entidad ImagenProducto existente.
     *
     * @param dto datos recibidos para actualizar la imagen
     * @param imagen entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarImagenProductoDTO dto,
            ImagenProducto imagen
    ) {
        imagen.setTextoAlternativo(dto.getTextoAlternativo());
        imagen.setOrden(dto.getOrden());
        imagen.setEsPortada(dto.getEsPortada());
    }

    /**
     * Busca un producto mediante su identificador.
     *
     * @param productoId identificador del producto
     * @return entidad Producto encontrada
     * @throws IllegalArgumentException si el producto no existe
     */
    private Producto obtenerProducto(UUID productoId) {

        return productoRepositorio.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));
    }
}