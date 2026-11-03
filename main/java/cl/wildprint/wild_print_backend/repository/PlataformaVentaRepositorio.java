package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de plataformas
 * externas de comercialización.
 * Permite consultar y administrar las plataformas utilizadas
 * para enlazar los productos con servicios externos de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface PlataformaVentaRepositorio extends JpaRepository<PlataformaVenta, UUID>{
}
