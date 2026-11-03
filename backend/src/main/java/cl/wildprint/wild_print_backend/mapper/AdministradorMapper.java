package cl.wildprint.wild_print_backend.mapper;

import cl.wildprint.wild_print_backend.dto.administrador.ActualizarAdministradorDTO;
import cl.wildprint.wild_print_backend.dto.administrador.AdministradorDTO;
import cl.wildprint.wild_print_backend.dto.administrador.CrearAdministradorDTO;
import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entidades UsuarioAdministrador y sus respectivos DTO.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
public class AdministradorMapper {

    /**
     * Convierte una entidad UsuarioAdministrador en su representación DTO.
     *
     * @param administrador entidad que se desea convertir
     * @return DTO con la información del administrador
     */
    public AdministradorDTO aDTO(UsuarioAdministrador administrador) {

        return AdministradorDTO.builder()
                .id(administrador.getId())
                .nombreUsuario(administrador.getNombreUsuario())
                .correo(administrador.getCorreo())
                .activo(administrador.isActivo())
                .fechaCreacion(administrador.getFechaCreacion())
                .fechaActualizacion(administrador.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte los datos de creación en una nueva entidad UsuarioAdministrador.
     *
     * @param dto datos recibidos para crear el administrador
     * @return nueva entidad UsuarioAdministrador
     */
    public UsuarioAdministrador desdeCrearDTO(CrearAdministradorDTO dto) {

        UsuarioAdministrador administrador = new UsuarioAdministrador();

        administrador.setNombreUsuario(dto.getNombreUsuario());
        administrador.setCorreo(dto.getCorreo());

        return administrador;
    }

    /**
     * Actualiza los datos de un administrador existente.
     *
     * @param dto datos recibidos para actualizar el administrador
     * @param administrador entidad que se desea modificar
     */
    public void actualizarDesdeDTO(
            ActualizarAdministradorDTO dto,
            UsuarioAdministrador administrador
    ) {

        administrador.setNombreUsuario(dto.getNombreUsuario());
        administrador.setCorreo(dto.getCorreo());
    }
}