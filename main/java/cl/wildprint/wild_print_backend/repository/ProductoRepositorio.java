package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de productos.
 * Permite consultar productos por identificador, slug y estado,
 * considerando si se encuentran en papelera.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ProductoRepositorio extends JpaRepository<Producto, UUID> {

    /**
     * Busca un producto por su slug que no se encuentre en papelera.
     *
     * @param slug Identificador textual del producto.
     * @return Producto encontrado, si existe.
     */
    Optional<Producto> findBySlugAndFechaEliminacionIsNull(String slug);

    /**
     * Obtiene todos los productos que no están en papelera.
     *
     * @return Lista de productos disponibles.
     */
    List<Producto> findAllByFechaEliminacionIsNull();

    /**
     * Obtiene los productos que coinciden con un estado
     * y que no se encuentran en papelera.
     *
     * @param estado Estado por el cual se desea filtrar.
     * @return Lista de productos encontrados.
     */
    List<Producto> findAllByEstadoAndFechaEliminacionIsNull(EstadoProducto estado);
}