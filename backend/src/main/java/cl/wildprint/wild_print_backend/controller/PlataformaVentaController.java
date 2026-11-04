package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.plataforma.PlataformaVentaDTO;
import cl.wildprint.wild_print_backend.mapper.PlataformaVentaMapper;
import cl.wildprint.wild_print_backend.service.PlataformaVentaServicio;
import cl.wildprint.wild_print_backend.dto.plataforma.CrearPlataformaVentaDTO;
import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import cl.wildprint.wild_print_backend.dto.plataforma.ActualizarPlataformaVentaDTO;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración
 * de plataformas de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/plataformas-venta")
@RequiredArgsConstructor
public class PlataformaVentaController {

    private final PlataformaVentaServicio plataformaVentaServicio;
    private final PlataformaVentaMapper plataformaVentaMapper;

    /**
     * Obtiene todas las plataformas registradas.
     *
     * @return lista de plataformas
     */
    @GetMapping
    public ResponseEntity<List<PlataformaVentaDTO>> listarPlataformas() {

        List<PlataformaVentaDTO> plataformas =
                plataformaVentaServicio.listarTodas()
                        .stream()
                        .map(plataformaVentaMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(plataformas);
    }

    /**
     * Obtiene las plataformas de venta que están activas.
     *
     * @return lista de plataformas activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<PlataformaVentaDTO>> listarPlataformasActivas() {

        List<PlataformaVentaDTO> plataformas =
                plataformaVentaServicio.listarActivas()
                        .stream()
                        .map(plataformaVentaMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(plataformas);
    }

    /**
     * Registra una nueva plataforma de venta.
     *
     * @param dto datos de la plataforma que se desea crear
     * @return plataforma creada
     */
    @PostMapping
    public ResponseEntity<PlataformaVentaDTO> crearPlataforma(
            @Valid @RequestBody CrearPlataformaVentaDTO dto
    ) {

        PlataformaVenta plataforma =
                plataformaVentaMapper.desdeCrearDTO(dto);

        PlataformaVenta plataformaCreada =
                plataformaVentaServicio.crear(plataforma);

        return ResponseEntity.status(201)
                .body(plataformaVentaMapper.aDTO(plataformaCreada));
    }

    /**
     * Actualiza los datos de una plataforma de venta existente.
     *
     * @param id identificador UUID de la plataforma
     * @param dto datos actualizados
     * @return plataforma actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlataformaVentaDTO> actualizarPlataforma(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarPlataformaVentaDTO dto
    ) {

        PlataformaVenta plataforma = plataformaVentaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la plataforma indicada"
                ));

        plataformaVentaMapper.actualizarDesdeDTO(dto, plataforma);

        PlataformaVenta plataformaActualizada =
                plataformaVentaServicio.actualizar(id, plataforma);

        return ResponseEntity.ok(
                plataformaVentaMapper.aDTO(plataformaActualizada)
        );
    }

    /**
     * Cambia el estado de disponibilidad de una plataforma.
     *
     * @param id identificador UUID de la plataforma
     * @param activa nuevo estado de disponibilidad
     * @return plataforma actualizada
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PlataformaVentaDTO> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam boolean activa
    ) {

        PlataformaVenta plataformaActualizada =
                plataformaVentaServicio.cambiarEstado(id, activa);

        return ResponseEntity.ok(
                plataformaVentaMapper.aDTO(plataformaActualizada)
        );
    }
}
