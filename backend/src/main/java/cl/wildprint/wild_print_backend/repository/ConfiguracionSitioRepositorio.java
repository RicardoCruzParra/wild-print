package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
