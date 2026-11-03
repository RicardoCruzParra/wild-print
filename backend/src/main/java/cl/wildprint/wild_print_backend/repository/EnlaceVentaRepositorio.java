package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.EnlaceVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de enlaces
 * externos de comercialización de productos.
 * Permite administrar los enlaces asociados a productos
 * y sus respectivas plataformas externas.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface EnlaceVentaRepositorio extends JpaRepository<EnlaceVenta, UUID> {
}
