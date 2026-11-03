package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.enlace.ActualizarEnlaceVentaDTO;
import cl.wildprint.wild_print_backend.dto.enlace.CrearEnlaceVentaDTO;
import cl.wildprint.wild_print_backend.dto.enlace.EnlaceVentaDTO;
import cl.wildprint.wild_print_backend.entity.EnlaceVenta;
import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.repository.PlataformaVentaRepositorio;
import cl.wildprint.wild_print_backend.repository.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper encargado de convertir entidades EnlaceVenta y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class EnlaceVentaMapper {

    private final ProductoRepositorio productoRepositorio;
    private final PlataformaVentaRepositorio plataformaVentaRepositorio;

    /**
     * Convierte una entidad EnlaceVenta en su representación DTO.
     *
     * @param enlace entidad que se desea convertir
     * @return DTO con la información del enlace
     */
    public EnlaceVentaDTO aDTO(EnlaceVenta enlace) {

        return EnlaceVentaDTO.builder()
                .id(enlace.getId())
                .productoId(enlace.getProducto().getId())
                .plataformaId(enlace.getPlataforma().getId())
                .urlExterna(enlace.getUrlExterna())
                .etiqueta(enlace.getEtiqueta())
                .activo(enlace.isActivo())
                .fechaCreacion(enlace.getFechaCreacion())
                .fechaActualizacion(enlace.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad EnlaceVenta.
     *
     * @param dto datos recibidos para crear el enlace
     * @return nueva entidad EnlaceVenta
     */
    public EnlaceVenta desdeCrearDTO(CrearEnlaceVentaDTO dto) {

        EnlaceVenta enlace = new EnlaceVenta();

        enlace.setProducto(obtenerProducto(dto.getProductoId()));
        enlace.setPlataforma(obtenerPlataforma(dto.getPlataformaId()));
        enlace.setUrlExterna(dto.getUrlExterna());
        enlace.setEtiqueta(dto.getEtiqueta());

        return enlace;
    }

    /**
     * Actualiza los datos de un enlace existente.
     *
     * @param dto datos recibidos para actualizar el enlace
     * @param enlace entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarEnlaceVentaDTO dto,
            EnlaceVenta enlace
    ) {

        enlace.setUrlExterna(dto.getUrlExterna());
        enlace.setEtiqueta(dto.getEtiqueta());
    }

    /**
     * Busca un producto mediante su identificador.
     *
     * @param productoId identificador del producto
     * @return entidad Producto encontrada
     */
    private Producto obtenerProducto(UUID productoId) {

        return productoRepositorio.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));
    }

    /**
     * Busca una plataforma mediante su identificador.
     *
     * @param plataformaId identificador de la plataforma
     * @return entidad PlataformaVenta encontrada
     */
    private PlataformaVenta obtenerPlataforma(UUID plataformaId) {

        return plataformaVentaRepositorio.findById(plataformaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la plataforma de venta indicada"
                ));
    }
}