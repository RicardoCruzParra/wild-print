package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.imagen.ActualizarImagenProductoDTO;
import cl.wildprint.wild_print_backend.dto.imagen.ImagenProductoDTO;
import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import cl.wildprint.wild_print_backend.storage.servicio.AlmacenamientoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entidades ImagenProducto y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class ImagenProductoMapper {

    private final AlmacenamientoServicio almacenamientoServicio;

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
                .url(almacenamientoServicio.obtenerUrl(imagen.getClaveArchivo()))
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
}