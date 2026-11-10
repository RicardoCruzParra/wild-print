package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.EnlaceVenta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de enlaces
 * externos de comercialización de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface EnlaceVentaRepositorio extends JpaRepository<EnlaceVenta, UUID> {

    /**
     * Busca un enlace por su identificador e inicializa
     * el producto y la plataforma asociados.
     *
     * @param id identificador del enlace
     * @return enlace encontrado, si existe
     */
    @Override
    @EntityGraph(attributePaths = {"producto", "plataforma"})
    Optional<EnlaceVenta> findById(UUID id);

    /**
     * Obtiene los enlaces asociados a un producto.
     *
     * @param productoId identificador del producto
     * @return lista de enlaces asociados
     */
    @EntityGraph(attributePaths = {"producto", "plataforma"})
    List<EnlaceVenta> findAllByProducto_Id(UUID productoId);
}