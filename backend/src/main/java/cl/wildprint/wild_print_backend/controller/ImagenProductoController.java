package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.imagen.ImagenProductoDTO;
import cl.wildprint.wild_print_backend.mapper.ImagenProductoMapper;
import cl.wildprint.wild_print_backend.service.ImagenProductoServicio;
import cl.wildprint.wild_print_backend.dto.imagen.CrearImagenProductoDTO;
import cl.wildprint.wild_print_backend.dto.imagen.ActualizarImagenProductoDTO;
import cl.wildprint.wild_print_backend.entity.ImagenProducto;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración
 * de imágenes asociadas a productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/productos/{productoId}/imagenes")
@RequiredArgsConstructor
public class ImagenProductoController {

    private final ImagenProductoServicio imagenProductoServicio;
    private final ImagenProductoMapper imagenProductoMapper;

    /**
     * Obtiene las imágenes asociadas a un producto.
     *
     * @param productoId identificador UUID del producto
     * @return lista de imágenes del producto
     */
    @GetMapping
    public ResponseEntity<List<ImagenProductoDTO>> listarImagenes(
            @PathVariable UUID productoId
    ) {

        List<ImagenProductoDTO> imagenes =
                imagenProductoServicio.listarPorProducto(productoId)
                        .stream()
                        .map(imagenProductoMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(imagenes);
    }

    /**
     * Registra los metadatos de una imagen asociada a un producto.
     *
     * @param productoId identificador UUID del producto
     * @param dto datos de la imagen
     * @return imagen registrada
     */
    @PostMapping
    public ResponseEntity<ImagenProductoDTO> crearImagen(
            @PathVariable UUID productoId,
            @Valid @RequestBody CrearImagenProductoDTO dto
    ) {

        ImagenProducto imagen = imagenProductoMapper.desdeCrearDTO(dto);

        ImagenProducto imagenCreada = imagenProductoServicio.crear(imagen);

        return ResponseEntity.status(201)
                .body(imagenProductoMapper.aDTO(imagenCreada));
    }

    /**
     * Actualiza los metadatos de una imagen existente.
     *
     * @param productoId identificador UUID del producto
     * @param id identificador UUID de la imagen
     * @param dto datos que se desean actualizar
     * @return imagen actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImagenProductoDTO> actualizarImagen(
            @PathVariable UUID productoId,
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarImagenProductoDTO dto
    ) {

        ImagenProducto imagen = imagenProductoServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen indicada"
                ));

        if (!imagen.getProducto().getId().equals(productoId)) {
            throw new IllegalArgumentException(
                    "La imagen no pertenece al producto indicado"
            );
        }

        imagenProductoMapper.actualizarDesdeDTO(dto, imagen);

        ImagenProducto imagenActualizada =
                imagenProductoServicio.actualizar(id, imagen);

        return ResponseEntity.ok(
                imagenProductoMapper.aDTO(imagenActualizada)
        );
    }

    /**
     * Elimina una imagen asociada a un producto.
     *
     * @param productoId identificador UUID del producto
     * @param id identificador UUID de la imagen
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagen(
            @PathVariable UUID productoId,
            @PathVariable UUID id
    ) {

        ImagenProducto imagen = imagenProductoServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen indicada"
                ));

        if (!imagen.getProducto().getId().equals(productoId)) {
            throw new IllegalArgumentException(
                    "La imagen no pertenece al producto indicado"
            );
        }

        imagenProductoServicio.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
