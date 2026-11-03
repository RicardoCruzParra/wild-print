package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
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

    /**
     * Busca una plataforma mediante su código único.
     *
     * @param codigo código de la plataforma
     * @return plataforma encontrada, si existe
     */
    Optional<PlataformaVenta> findByCodigo(String codigo);

    /**
     * Obtiene todas las plataformas activas.
     *
     * @return lista de plataformas activas
     */
    List<PlataformaVenta> findAllByActivaTrue();
}
