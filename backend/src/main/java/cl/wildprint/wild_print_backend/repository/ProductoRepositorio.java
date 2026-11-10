package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ProductoRepositorio extends JpaRepository<Producto, UUID> {

    /**
     * Busca un producto por su identificador e inicializa sus categorías.
     *
     * @param id identificador del producto
     * @return producto encontrado, si existe
     */
    @Override
    @EntityGraph(attributePaths = "categorias")
    Optional<Producto> findById(UUID id);

    /**
     * Busca un producto por su slug que no se encuentre en papelera.
     *
     * @param slug identificador textual del producto
     * @return producto encontrado, si existe
     */
    @EntityGraph(attributePaths = "categorias")
    Optional<Producto> findBySlugAndFechaEliminacionIsNull(String slug);

    /**
     * Obtiene todos los productos que no están en papelera.
     *
     * @return lista de productos disponibles
     */
    @EntityGraph(attributePaths = "categorias")
    List<Producto> findAllByFechaEliminacionIsNull();

    /**
     * Obtiene los productos que coinciden con un estado
     * y que no se encuentran en papelera.
     *
     * @param estado estado por el cual se desea filtrar
     * @return lista de productos encontrados
     */
    @EntityGraph(attributePaths = "categorias")
    List<Producto> findAllByEstadoAndFechaEliminacionIsNull(EstadoProducto estado);
}