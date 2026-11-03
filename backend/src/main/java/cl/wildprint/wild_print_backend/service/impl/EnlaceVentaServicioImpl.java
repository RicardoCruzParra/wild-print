package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.EnlaceVenta;
import cl.wildprint.wild_print_backend.repository.EnlaceVentaRepositorio;
import cl.wildprint.wild_print_backend.service.EnlaceVentaServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de enlaces de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EnlaceVentaServicioImpl implements EnlaceVentaServicio {

    /**
     * Repositorio encargado del acceso a los enlaces de venta.
     */
    private final EnlaceVentaRepositorio enlaceVentaRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public EnlaceVenta crear(EnlaceVenta enlace) {
        return enlaceVentaRepositorio.save(enlace);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnlaceVenta actualizar(UUID id, EnlaceVenta enlace) {
        EnlaceVenta enlaceExistente = obtenerEnlace(id);

        enlaceExistente.setUrlExterna(enlace.getUrlExterna());
        enlaceExistente.setEtiqueta(enlace.getEtiqueta());
        enlaceExistente.setActivo(enlace.isActivo());

        return enlaceVentaRepositorio.save(enlaceExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnlaceVenta> buscarPorId(UUID id) {
        return enlaceVentaRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnlaceVenta> listarPorProducto(UUID productoId) {
        return enlaceVentaRepositorio.findAllByProducto_Id(productoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnlaceVenta> listarTodos() {
        return enlaceVentaRepositorio.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnlaceVenta cambiarEstado(UUID id, boolean activo) {
        EnlaceVenta enlace = obtenerEnlace(id);

        enlace.setActivo(activo);

        return enlaceVentaRepositorio.save(enlace);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(UUID id) {
        EnlaceVenta enlace = obtenerEnlace(id);

        enlaceVentaRepositorio.delete(enlace);
    }

    /**
     * Obtiene un enlace existente o genera una excepción.
     *
     * @param id identificador del enlace
     * @return enlace encontrado
     * @throws IllegalArgumentException si el enlace no existe
     */
    private EnlaceVenta obtenerEnlace(UUID id) {
        return enlaceVentaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el enlace de venta con identificador: " + id));
    }
}