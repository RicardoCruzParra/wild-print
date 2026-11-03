package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.PlataformaVenta;
import cl.wildprint.wild_print_backend.repository.PlataformaVentaRepositorio;
import cl.wildprint.wild_print_backend.service.PlataformaVentaServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de plataformas de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PlataformaVentaServicioImpl implements PlataformaVentaServicio {

    /**
     * Repositorio encargado del acceso a las plataformas de venta.
     */
    private final PlataformaVentaRepositorio plataformaVentaRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public PlataformaVenta crear(PlataformaVenta plataforma) {
        return plataformaVentaRepositorio.save(plataforma);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlataformaVenta actualizar(UUID id, PlataformaVenta plataforma) {
        PlataformaVenta plataformaExistente = obtenerPlataforma(id);

        plataformaExistente.setCodigo(plataforma.getCodigo());
        plataformaExistente.setNombre(plataforma.getNombre());

        return plataformaVentaRepositorio.save(plataformaExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlataformaVenta> buscarPorId(UUID id) {
        return plataformaVentaRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlataformaVenta> buscarPorCodigo(String codigo) {
        return plataformaVentaRepositorio.findByCodigo(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlataformaVenta> listarTodas() {
        return plataformaVentaRepositorio.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlataformaVenta> listarActivas() {
        return plataformaVentaRepositorio.findAllByActivaTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlataformaVenta cambiarEstado(UUID id, boolean activa) {
        PlataformaVenta plataforma = obtenerPlataforma(id);

        plataforma.setActiva(activa);

        return plataformaVentaRepositorio.save(plataforma);
    }

    /**
     * Obtiene una plataforma existente o genera una excepción.
     *
     * @param id identificador de la plataforma
     * @return plataforma encontrada
     * @throws IllegalArgumentException si la plataforma no existe
     */
    private PlataformaVenta obtenerPlataforma(UUID id) {
        return plataformaVentaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la plataforma con identificador: " + id));
    }
}