package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de imágenes de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ImagenProductoServicio {

    /**
     * Registra los metadatos de una imagen.
     *
     * @param imagen imagen que se desea registrar
     * @return imagen registrada
     */
    ImagenProducto crear(ImagenProducto imagen);

    /**
     * Almacena un archivo de imagen y registra sus metadatos
     * asociándolo al producto indicado.
     *
     * @param productoId identificador del producto
     * @param archivo archivo de imagen que se desea almacenar
     * @param textoAlternativo texto alternativo para accesibilidad
     * @param orden posición de la imagen dentro de la galería
     * @param esPortada indica si la imagen será la portada del producto
     * @return imagen registrada
     * @throws IllegalArgumentException si el producto no existe o está en papelera
     */
    ImagenProducto crearDesdeArchivo(
            UUID productoId,
            MultipartFile archivo,
            String textoAlternativo,
            Integer orden,
            boolean esPortada
    );

    /**
     * Actualiza los metadatos de una imagen existente.
     *
     * @param id identificador de la imagen
     * @param imagen datos actualizados
     * @return imagen actualizada
     */
    ImagenProducto actualizar(UUID id, ImagenProducto imagen);

    /**
     * Busca una imagen por su identificador.
     *
     * @param id identificador de la imagen
     * @return imagen encontrada, si existe
     */
    Optional<ImagenProducto> buscarPorId(UUID id);

    /**
     * Obtiene las imágenes asociadas a un producto.
     *
     * @param productoId identificador del producto
     * @return lista de imágenes del producto
     */
    List<ImagenProducto> listarPorProducto(UUID productoId);

    /**
     * Comprueba si un producto tiene una imagen de portada.
     *
     * @param productoId identificador del producto
     * @return true si existe una portada
     */
    boolean existePortada(UUID productoId);

    /**
     * Elimina los metadatos y el archivo físico de una imagen.
     *
     * @param id identificador de la imagen
     */
    void eliminar(UUID id);
}