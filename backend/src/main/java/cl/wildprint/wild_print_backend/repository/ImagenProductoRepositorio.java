package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de imágenes
 * asociadas a los productos.
 * Permite consultar los metadatos de imágenes y verificar
 * la existencia de una imagen de portada.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ImagenProductoRepositorio extends JpaRepository<ImagenProducto, UUID> {

    /**
     * Comprueba si un producto tiene una imagen marcada como portada.
     *
     * @param productoId Identificador UUID del producto.
     * @return TRUE si existe una imagen de portada; FALSE en caso contrario.
     */
    boolean existsByProducto_IdAndEsPortadaTrue(UUID productoId);

    /**
     * Obtiene las imágenes de un producto ordenadas por su posición.
     *
     * @param productoId identificador del producto
     * @return imágenes ordenadas ascendentemente
     */
    List<ImagenProducto> findAllByProducto_IdOrderByOrdenAsc(UUID productoId);
}