package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import cl.wildprint.wild_print_backend.entity.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Servicio que define las operaciones de negocio relacionadas
 * con la administración de productos de Wild Print.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ProductoServicio {

    /**
     * Crea un nuevo producto.
     *
     * @param producto Datos del producto que se desea crear.
     * @return Producto creado y persistido.
     */
    Producto crear(Producto producto);

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id Identificador UUID del producto.
     * @param producto Nuevos datos del producto.
     * @return Producto actualizado.
     * @throws IllegalArgumentException Si el producto no existe o está en papelera.
     */
    Producto actualizar(UUID id, Producto producto);

    /**
     * Busca un producto por su identificador UUID.
     *
     * @param id Identificador UUID del producto.
     * @return Optional con el producto si existe y no está en papelera.
     */
    Optional<Producto> buscarPorId(UUID id);

    /**
     * Busca un producto mediante su slug.
     *
     * @param slug Identificador textual utilizado en la URL del producto.
     * @return Optional con el producto encontrado.
     */
    Optional<Producto> buscarPorSlug(String slug);

    /**
     * Obtiene todos los productos que no se encuentran en papelera.
     *
     * @return Lista de productos activos.
     */
    List<Producto> listarTodos();

    /**
     * Obtiene los productos que coinciden con un estado determinado.
     *
     * @param estado Estado por el cual se desea filtrar.
     * @return Lista de productos que coinciden con el estado indicado.
     */
    List<Producto> listarPorEstado(EstadoProducto estado);

    /**
     * Envía un producto a la papelera mediante su marca de eliminación.
     *
     * @param id Identificador UUID del producto.
     * @throws IllegalArgumentException Si el producto no existe o ya está en papelera.
     */
    void enviarAPapelera(UUID id);

    /**
     * Restaura un producto que se encuentra en papelera.
     * El producto recuperado vuelve al estado BORRADOR.
     *
     * @param id Identificador UUID del producto.
     * @return Producto restaurado.
     * @throws IllegalArgumentException Si el producto no existe.
     * @throws IllegalStateException Si el producto no está en papelera.
     */
    Producto restaurar(UUID id);

    /**
     * Elimina definitivamente un producto y sus recursos asociados.
     *
     * @param id Identificador UUID del producto.
     */
    void eliminarDefinitivamente(UUID id);
}