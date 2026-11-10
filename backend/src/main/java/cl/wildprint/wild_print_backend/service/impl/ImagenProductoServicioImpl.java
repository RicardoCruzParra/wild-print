package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.ImagenProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.repository.ImagenProductoRepositorio;
import cl.wildprint.wild_print_backend.repository.ProductoRepositorio;
import cl.wildprint.wild_print_backend.service.ImagenProductoServicio;
import cl.wildprint.wild_print_backend.storage.modelo.ArchivoAlmacenado;
import cl.wildprint.wild_print_backend.storage.servicio.AlmacenamientoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final ImagenProductoRepositorio imagenProductoRepositorio;
    private final ProductoRepositorio productoRepositorio;
    private final AlmacenamientoServicio almacenamientoServicio;

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
    public ImagenProducto crearDesdeArchivo(
            UUID productoId,
            MultipartFile archivo,
            String textoAlternativo,
            Integer orden,
            boolean esPortada
    ) {

        Producto producto = productoRepositorio.findById(productoId)
                .filter(p -> p.getFechaEliminacion() == null)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el producto indicado"
                ));

        ArchivoAlmacenado almacenado = almacenamientoServicio.almacenar(archivo);

        try {
            if (esPortada) {
                imagenProductoRepositorio.desmarcarPortadas(productoId);
            }

            ImagenProducto imagen = new ImagenProducto();
            imagen.setProducto(producto);
            imagen.setClaveArchivo(almacenado.clave());
            imagen.setNombreOriginal(archivo.getOriginalFilename());
            imagen.setTipoContenido(archivo.getContentType());
            imagen.setTamanoBytes(archivo.getSize());
            imagen.setTextoAlternativo(textoAlternativo);
            imagen.setOrden(orden == null ? 0 : orden);
            imagen.setEsPortada(esPortada);

            return imagenProductoRepositorio.save(imagen);

        } catch (RuntimeException e) {
            almacenamientoServicio.eliminar(almacenado.clave());
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImagenProducto actualizar(UUID id, ImagenProducto imagen) {

        ImagenProducto imagenExistente = imagenProductoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen con identificador: " + id));

        imagenExistente.setTextoAlternativo(imagen.getTextoAlternativo());
        imagenExistente.setOrden(imagen.getOrden());

        if (imagen.isEsPortada()) {
            imagenProductoRepositorio.desmarcarOtrasPortadas(
                    imagenExistente.getProducto().getId(),
                    id
            );
        }

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
        return imagenProductoRepositorio
                .findAllByProducto_IdOrderByOrdenAsc(productoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existePortada(UUID productoId) {
        return imagenProductoRepositorio
                .existsByProducto_IdAndEsPortadaTrue(productoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(UUID id) {

        ImagenProducto imagen = imagenProductoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la imagen con identificador: " + id));

        String clave = imagen.getClaveArchivo();

        imagenProductoRepositorio.delete(imagen);
        almacenamientoServicio.eliminar(clave);
    }
}