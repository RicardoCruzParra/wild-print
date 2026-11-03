package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.ConfiguracionSitio;
import cl.wildprint.wild_print_backend.repository.ConfiguracionSitioRepositorio;
import cl.wildprint.wild_print_backend.service.ConfiguracionSitioServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de la configuración del sitio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ConfiguracionSitioServicioImpl implements ConfiguracionSitioServicio {

    /**
     * Repositorio encargado del acceso a la configuración del sitio.
     */
    private final ConfiguracionSitioRepositorio configuracionSitioRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfiguracionSitio crear(ConfiguracionSitio configuracion) {
        return configuracionSitioRepositorio.save(configuracion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfiguracionSitio actualizar(UUID id, String valor) {
        ConfiguracionSitio configuracion = obtenerConfiguracion(id);

        configuracion.setValor(valor);

        return configuracionSitioRepositorio.save(configuracion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfiguracionSitio> buscarPorId(UUID id) {
        return configuracionSitioRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfiguracionSitio> buscarPorClave(String clave) {
        return configuracionSitioRepositorio.findByClave(clave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionSitio> listarTodas() {
        return configuracionSitioRepositorio.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(UUID id) {
        ConfiguracionSitio configuracion = obtenerConfiguracion(id);

        configuracionSitioRepositorio.delete(configuracion);
    }

    /**
     * Obtiene una configuración existente o genera una excepción.
     *
     * @param id identificador de la configuración
     * @return configuración encontrada
     * @throws IllegalArgumentException si la configuración no existe
     */
    private ConfiguracionSitio obtenerConfiguracion(UUID id) {
        return configuracionSitioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la configuración con identificador: " + id));
    }
}