package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de configuraciones
 * dinámicas del sitio web.
 * Permite consultar y administrar parámetros identificados
 * mediante claves únicas.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ConfiguracionSitioRepositorio extends JpaRepository<ConfiguracionSitio, UUID>{

    /**
     * Busca una configuración mediante su clave única.
     *
     * @param clave clave de configuración
     * @return configuración encontrada, si existe
     */
    Optional<ConfiguracionSitio> findByClave(String clave);
}
