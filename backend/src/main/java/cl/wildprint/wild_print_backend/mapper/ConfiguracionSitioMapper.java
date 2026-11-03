package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.configuracion.ActualizarConfiguracionSitioDTO;
import cl.wildprint.wild_print_backend.dto.configuracion.ConfiguracionSitioDTO;
import cl.wildprint.wild_print_backend.dto.configuracion.CrearConfiguracionSitioDTO;
import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entidades ConfiguracionSitio y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
public class ConfiguracionSitioMapper {

    /**
     * Convierte una entidad ConfiguracionSitio en su representación DTO.
     *
     * @param configuracion entidad que se desea convertir
     * @return DTO con la información de la configuración
     */
    public ConfiguracionSitioDTO aDTO(ConfiguracionSitio configuracion) {

        return ConfiguracionSitioDTO.builder()
                .id(configuracion.getId())
                .clave(configuracion.getClave())
                .valor(configuracion.getValor())
                .fechaActualizacion(configuracion.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad ConfiguracionSitio.
     *
     * @param dto datos recibidos para crear la configuración
     * @return nueva entidad ConfiguracionSitio
     */
    public ConfiguracionSitio desdeCrearDTO(CrearConfiguracionSitioDTO dto) {

        ConfiguracionSitio configuracion = new ConfiguracionSitio();

        configuracion.setClave(dto.getClave());
        configuracion.setValor(dto.getValor());

        return configuracion;
    }

    /**
     * Actualiza el valor de una configuración existente.
     *
     * @param dto datos recibidos para actualizar la configuración
     * @param configuracion entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarConfiguracionSitioDTO dto,
            ConfiguracionSitio configuracion
    ) {

        configuracion.setValor(dto.getValor());
    }
}