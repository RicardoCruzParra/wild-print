package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.enlace.EnlaceVentaDTO;
import cl.wildprint.wild_print_backend.mapper.EnlaceVentaMapper;
import cl.wildprint.wild_print_backend.service.EnlaceVentaServicio;
import cl.wildprint.wild_print_backend.dto.enlace.CrearEnlaceVentaDTO;
import cl.wildprint.wild_print_backend.dto.enlace.ActualizarEnlaceVentaDTO;
import cl.wildprint.wild_print_backend.entity.EnlaceVenta;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración
 * de enlaces de venta asociados a productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/productos/{productoId}/enlaces-venta")
@RequiredArgsConstructor
public class EnlaceVentaController {

    private final EnlaceVentaServicio enlaceVentaServicio;
    private final EnlaceVentaMapper enlaceVentaMapper;

    /**
     * Obtiene los enlaces de venta asociados a un producto.
     *
     * @param productoId identificador UUID del producto
     * @return lista de enlaces de venta
     */
    @GetMapping
    public ResponseEntity<List<EnlaceVentaDTO>> listarEnlaces(
            @PathVariable UUID productoId
    ) {

        List<EnlaceVentaDTO> enlaces =
                enlaceVentaServicio.listarPorProducto(productoId)
                        .stream()
                        .map(enlaceVentaMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(enlaces);
    }

    /**
     * Registra un enlace de venta asociado a un producto.
     *
     * @param productoId identificador UUID del producto
     * @param dto datos del enlace que se desea registrar
     * @return enlace creado
     */
    @PostMapping
    public ResponseEntity<EnlaceVentaDTO> crearEnlace(
            @PathVariable UUID productoId,
            @Valid @RequestBody CrearEnlaceVentaDTO dto
    ) {

        if (!productoId.equals(dto.getProductoId())) {
            throw new IllegalArgumentException(
                    "El producto del enlace no coincide con el producto de la ruta"
            );
        }

        EnlaceVenta enlace = enlaceVentaMapper.desdeCrearDTO(dto);

        EnlaceVenta enlaceCreado = enlaceVentaServicio.crear(enlace);

        return ResponseEntity.status(201)
                .body(enlaceVentaMapper.aDTO(enlaceCreado));
    }

    /**
     * Actualiza los datos de un enlace de venta existente.
     *
     * @param productoId identificador UUID del producto
     * @param id identificador UUID del enlace
     * @param dto datos actualizados
     * @return enlace actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnlaceVentaDTO> actualizarEnlace(
            @PathVariable UUID productoId,
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarEnlaceVentaDTO dto
    ) {

        EnlaceVenta enlace = enlaceVentaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el enlace indicado"
                ));

        if (!enlace.getProducto().getId().equals(productoId)) {
            throw new IllegalArgumentException(
                    "El enlace no pertenece al producto indicado"
            );
        }

        enlaceVentaMapper.actualizarDesdeDTO(dto, enlace);

        EnlaceVenta enlaceActualizado =
                enlaceVentaServicio.actualizar(id, enlace);

        return ResponseEntity.ok(
                enlaceVentaMapper.aDTO(enlaceActualizado)
        );
    }

    /**
     * Cambia el estado de disponibilidad de un enlace de venta.
     *
     * @param productoId identificador UUID del producto
     * @param id identificador UUID del enlace
     * @param activo nuevo estado de disponibilidad
     * @return enlace actualizado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EnlaceVentaDTO> cambiarEstado(
            @PathVariable UUID productoId,
            @PathVariable UUID id,
            @RequestParam boolean activo
    ) {

        EnlaceVenta enlace = enlaceVentaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el enlace indicado"
                ));

        if (!enlace.getProducto().getId().equals(productoId)) {
            throw new IllegalArgumentException(
                    "El enlace no pertenece al producto indicado"
            );
        }

        EnlaceVenta enlaceActualizado =
                enlaceVentaServicio.cambiarEstado(id, activo);

        return ResponseEntity.ok(
                enlaceVentaMapper.aDTO(enlaceActualizado)
        );
    }

    /**
     * Elimina un enlace de venta asociado a un producto.
     *
     * @param productoId identificador UUID del producto
     * @param id identificador UUID del enlace
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnlace(
            @PathVariable UUID productoId,
            @PathVariable UUID id
    ) {

        EnlaceVenta enlace = enlaceVentaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el enlace indicado"
                ));

        if (!enlace.getProducto().getId().equals(productoId)) {
            throw new IllegalArgumentException(
                    "El enlace no pertenece al producto indicado"
            );
        }

        enlaceVentaServicio.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
