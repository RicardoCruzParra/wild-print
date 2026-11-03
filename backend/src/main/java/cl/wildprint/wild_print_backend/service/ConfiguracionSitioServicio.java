package cl.wildprint.wild_print_backend.service;


import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de la configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface ConfiguracionSitioServicio {

    /**
     * Registra una nueva configuración del sitio.
     *
     * @param configuracion configuración que se desea registrar
     * @return configuración creada
     */
    ConfiguracionSitio crear(ConfiguracionSitio configuracion);

    /**
     * Actualiza el valor de una configuración existente.
     *
     * @param id identificador de la configuración
     * @param valor nuevo valor de configuración
     * @return configuración actualizada
     */
    ConfiguracionSitio actualizar(UUID id, String valor);

    /**
     * Busca una configuración por su identificador.
     *
     * @param id identificador de la configuración
     * @return configuración encontrada, si existe
     */
    Optional<ConfiguracionSitio> buscarPorId(UUID id);

    /**
     * Busca una configuración mediante su clave.
     *
     * @param clave clave única de configuración
     * @return configuración encontrada, si existe
     */
    Optional<ConfiguracionSitio> buscarPorClave(String clave);

    /**
     * Obtiene todas las configuraciones registradas.
     *
     * @return lista de configuraciones
     */
    List<ConfiguracionSitio> listarTodas();

    /**
     * Elimina una configuración existente.
     *
     * @param id identificador de la configuración
     */
    void eliminar(UUID id);
}