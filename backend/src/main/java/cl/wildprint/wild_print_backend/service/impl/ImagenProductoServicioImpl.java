package cl.wildprint.wild_print_backend.service.impl;


import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import cl.wildprint.wild_print_backend.repository.ImagenProductoRepositorio;
import cl.wildprint.wild_print_backend.service.ImagenProductoServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de imágenes de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ImagenProductoServicioImpl implements ImagenProductoServicio {

    /**
     * Repositorio encargado del acceso a los metadatos de imágenes.
     */
    private final ImagenProductoRepositorio imagenProductoRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public ImagenProducto crear(ImagenProducto imagen) {
        return imagenProductoRepositorio.save(imagen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImagenProducto actualizar(UUID id, ImagenProducto imagen) {
        ImagenProducto imagenExistente = imagenProductoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen con identificador: " + id));

        imagenExistente.setNombreOriginal(imagen.getNombreOriginal());
        imagenExistente.setTextoAlternativo(imagen.getTextoAlternativo());
        imagenExistente.setOrden(imagen.getOrden());
        imagenExistente.setEsPortada(imagen.isEsPortada());

        return imagenProductoRepositorio.save(imagenExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImagenProducto> buscarPorId(UUID id) {
        return imagenProductoRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImagenProducto> listarPorProducto(UUID productoId) {
        return imagenProductoRepositorio.findAllByProducto_IdOrderByOrdenAsc(productoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existePortada(UUID productoId) {
        return imagenProductoRepositorio.existsByProducto_IdAndEsPortadaTrue(productoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(UUID id) {
        ImagenProducto imagen = imagenProductoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen con identificador: " + id));

        imagenProductoRepositorio.delete(imagen);
    }
}
