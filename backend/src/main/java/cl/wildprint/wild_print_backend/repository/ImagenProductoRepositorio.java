package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de imágenes
 * asociadas a los productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ImagenProductoRepositorio
        extends JpaRepository<ImagenProducto, UUID> {

    /**
     * Comprueba si un producto tiene una imagen marcada como portada.
     *
     * @param productoId identificador UUID del producto
     * @return true si existe una imagen de portada
     */
    boolean existsByProducto_IdAndEsPortadaTrue(UUID productoId);

    /**
     * Obtiene las imágenes de un producto ordenadas por su posición.
     *
     * @param productoId identificador del producto
     * @return imágenes ordenadas ascendentemente
     */
    List<ImagenProducto> findAllByProducto_IdOrderByOrdenAsc(UUID productoId);

    /**
     * Desmarca todas las imágenes de portada de un producto.
     *
     * @param productoId identificador del producto
     */
    @Modifying
    @Query("update ImagenProducto i set i.esPortada = false " +
            "where i.producto.id = :productoId and i.esPortada = true")
    void desmarcarPortadas(@Param("productoId") UUID productoId);

    /**
     * Desmarca las portadas de un producto excepto la imagen indicada.
     *
     * @param productoId identificador del producto
     * @param imagenId identificador de la imagen que conserva la portada
     */
    @Modifying
    @Query("update ImagenProducto i set i.esPortada = false " +
            "where i.producto.id = :productoId and i.esPortada = true " +
            "and i.id <> :imagenId")
    void desmarcarOtrasPortadas(
            @Param("productoId") UUID productoId,
            @Param("imagenId") UUID imagenId
    );
}