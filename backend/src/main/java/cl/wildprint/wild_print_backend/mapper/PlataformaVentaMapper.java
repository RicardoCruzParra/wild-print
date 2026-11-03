package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.plataforma.ActualizarPlataformaVentaDTO;
import cl.wildprint.wild_print_backend.dto.plataforma.CrearPlataformaVentaDTO;
import cl.wildprint.wild_print_backend.dto.plataforma.PlataformaVentaDTO;
import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entidades PlataformaVenta y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
public class PlataformaVentaMapper {

    /**
     * Convierte una entidad PlataformaVenta en su representación DTO.
     *
     * @param plataforma entidad que se desea convertir
     * @return DTO con la información de la plataforma
     */
    public PlataformaVentaDTO aDTO(PlataformaVenta plataforma) {

        return PlataformaVentaDTO.builder()
                .id(plataforma.getId())
                .codigo(plataforma.getCodigo())
                .nombre(plataforma.getNombre())
                .activa(plataforma.isActiva())
                .fechaCreacion(plataforma.getFechaCreacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad PlataformaVenta.
     *
     * @param dto datos recibidos para crear la plataforma
     * @return nueva entidad PlataformaVenta
     */
    public PlataformaVenta desdeCrearDTO(CrearPlataformaVentaDTO dto) {

        PlataformaVenta plataforma = new PlataformaVenta();

        plataforma.setCodigo(dto.getCodigo());
        plataforma.setNombre(dto.getNombre());

        return plataforma;
    }

    /**
     * Actualiza los datos de una entidad PlataformaVenta existente.
     *
     * @param dto datos recibidos para actualizar la plataforma
     * @param plataforma entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarPlataformaVentaDTO dto,
            PlataformaVenta plataforma
    ) {

        plataforma.setCodigo(dto.getCodigo());
        plataforma.setNombre(dto.getNombre());
    }
}
