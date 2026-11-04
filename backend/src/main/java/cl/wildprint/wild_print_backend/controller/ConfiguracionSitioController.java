package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.configuracion.ConfiguracionSitioDTO;
import cl.wildprint.wild_print_backend.mapper.ConfiguracionSitioMapper;
import cl.wildprint.wild_print_backend.service.ConfiguracionSitioServicio;
import cl.wildprint.wild_print_backend.dto.configuracion.CrearConfiguracionSitioDTO;
import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;
import cl.wildprint.wild_print_backend.dto.configuracion.ActualizarConfiguracionSitioDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración
 * de la configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
public class ConfiguracionSitioController {

    private final ConfiguracionSitioServicio configuracionSitioServicio;
    private final ConfiguracionSitioMapper configuracionSitioMapper;

    /**
     * Obtiene todas las configuraciones registradas.
     *
     * @return lista de configuraciones
     */
    @GetMapping
    public ResponseEntity<List<ConfiguracionSitioDTO>> listarConfiguraciones() {

        List<ConfiguracionSitioDTO> configuraciones =
                configuracionSitioServicio.listarTodas()
                        .stream()
                        .map(configuracionSitioMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(configuraciones);
    }

    /**
     * Registra una nueva configuración del sitio.
     *
     * @param dto datos de la configuración que se desea registrar
     * @return configuración creada
     */
    @PostMapping
    public ResponseEntity<ConfiguracionSitioDTO> crearConfiguracion(
            @Valid @RequestBody CrearConfiguracionSitioDTO dto
    ) {

        ConfiguracionSitio configuracion =
                configuracionSitioMapper.desdeCrearDTO(dto);

        ConfiguracionSitio configuracionCreada =
                configuracionSitioServicio.crear(configuracion);

        return ResponseEntity.status(201)
                .body(configuracionSitioMapper.aDTO(configuracionCreada));
    }

    /**
     * Actualiza el valor de una configuración existente.
     *
     * @param id identificador UUID de la configuración
     * @param dto nuevo valor de configuración
     * @return configuración actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionSitioDTO> actualizarConfiguracion(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarConfiguracionSitioDTO dto
    ) {

        ConfiguracionSitio configuracion =
                configuracionSitioServicio.buscarPorId(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe la configuración indicada"
                        ));

        configuracionSitioMapper.actualizarDesdeDTO(dto, configuracion);

        ConfiguracionSitio configuracionActualizada =
                configuracionSitioServicio.actualizar(
                        id,
                        configuracion.getValor()
                );

        return ResponseEntity.ok(
                configuracionSitioMapper.aDTO(configuracionActualizada)
        );
    }

    /**
     * Busca una configuración mediante su clave.
     *
     * @param clave clave única de configuración
     * @return configuración encontrada
     */
    @GetMapping("/clave/{clave}")
    public ResponseEntity<ConfiguracionSitioDTO> buscarPorClave(
            @PathVariable String clave
    ) {

        ConfiguracionSitio configuracion =
                configuracionSitioServicio.buscarPorClave(clave)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe la configuración indicada"
                        ));

        return ResponseEntity.ok(
                configuracionSitioMapper.aDTO(configuracion)
        );
    }

    /**
     * Elimina una configuración existente.
     *
     * @param id identificador UUID de la configuración
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConfiguracion(
            @PathVariable UUID id
    ) {

        configuracionSitioServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la configuración indicada"
                ));

        configuracionSitioServicio.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
