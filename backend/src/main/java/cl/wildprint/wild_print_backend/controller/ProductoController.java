package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.producto.ActualizarProductoDTO;
import cl.wildprint.wild_print_backend.dto.producto.CrearProductoDTO;
import cl.wildprint.wild_print_backend.dto.producto.ProductoDTO;
import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.mapper.ProductoMapper;
import cl.wildprint.wild_print_backend.service.ProductoServicio;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller encargado de gestionar las operaciones REST de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServicio productoServicio;
    private final ProductoMapper productoMapper;

    /**
     * Obtiene los productos publicados disponibles en el catálogo.
     *
     * @return listado de productos publicados
     */
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {

        List<ProductoDTO> productos = productoServicio
                .listarPorEstado(EstadoProducto.PUBLICADO)
                .stream()
                .map(productoMapper::aDTO)
                .toList();

        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto publicado mediante su identificador.
     *
     * @param id identificador UUID del producto
     * @return producto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(
            @PathVariable UUID id
    ) {

        Producto producto = productoServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));

        if (producto.getEstado() != EstadoProducto.PUBLICADO) {
            throw new IllegalArgumentException(
                    "El producto solicitado no está publicado"
            );
        }

        return ResponseEntity.ok(productoMapper.aDTO(producto));
    }

    /**
     * Crea un nuevo producto en el catálogo.
     *
     * @param dto datos recibidos para crear el producto
     * @return producto creado
     */
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(
            @Valid @RequestBody CrearProductoDTO dto
    ) {

        Producto producto = productoMapper.desdeCrearDTO(dto);

        Producto productoCreado = productoServicio.crear(producto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoMapper.aDTO(productoCreado));
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id identificador UUID del producto
     * @param dto datos recibidos para actualizar el producto
     * @return producto actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarProductoDTO dto
    ) {

        Producto producto = productoServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));

        productoMapper.actualizarDesdeDTO(dto, producto);

        Producto productoActualizado = productoServicio.actualizar(id, producto);

        return ResponseEntity.ok(
                productoMapper.aDTO(productoActualizado)
        );
    }

    /**
     * Obtiene un producto publicado mediante su slug.
     *
     * @param slug identificador textual del producto
     * @return producto encontrado
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorSlug(
            @PathVariable String slug
    ) {

        Producto producto = productoServicio.buscarPorSlug(slug)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));

        if (producto.getEstado() != EstadoProducto.PUBLICADO) {
            throw new IllegalArgumentException(
                    "El producto solicitado no está publicado"
            );
        }

        return ResponseEntity.ok(productoMapper.aDTO(producto));
    }

    /**
     * Obtiene todos los productos disponibles para administración.
     *
     * @return listado de productos que no están en papelera
     */
    @GetMapping("/administracion")
    public ResponseEntity<List<ProductoDTO>> listarProductosAdministracion() {

        List<ProductoDTO> productos = productoServicio.listarTodos()
                .stream()
                .map(productoMapper::aDTO)
                .toList();

        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene los productos que coinciden con un estado determinado.
     *
     * @param estado estado por el cual se desea filtrar
     * @return listado de productos del estado indicado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoDTO>> listarPorEstado(
            @PathVariable EstadoProducto estado
    ) {

        List<ProductoDTO> productos = productoServicio.listarPorEstado(estado)
                .stream()
                .map(productoMapper::aDTO)
                .toList();

        return ResponseEntity.ok(productos);
    }

    /**
     * Envía un producto a la papelera.
     *
     * @param id identificador UUID del producto
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> enviarAPapelera(
            @PathVariable UUID id
    ) {

        productoServicio.enviarAPapelera(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Restaura un producto que se encuentra en papelera.
     *
     * @param id identificador UUID del producto
     * @return producto restaurado
     */
    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<ProductoDTO> restaurarProducto(
            @PathVariable UUID id
    ) {

        Producto productoRestaurado = productoServicio.restaurar(id);

        return ResponseEntity.ok(
                productoMapper.aDTO(productoRestaurado)
        );
    }

    /**
     * Elimina definitivamente un producto de la papelera.
     *
     * @param id identificador UUID del producto
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}/definitivo")
    public ResponseEntity<Void> eliminarDefinitivamente(
            @PathVariable UUID id
    ) {
        productoServicio.eliminarDefinitivamente(id);

        return ResponseEntity.noContent().build();
    }
}