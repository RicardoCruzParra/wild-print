package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.config.PropiedadesWildPrint;
import cl.wildprint.wild_print_backend.entity.EstadoProducto;
import cl.wildprint.wild_print_backend.entity.Producto;
import cl.wildprint.wild_print_backend.repository.ImagenProductoRepositorio;
import cl.wildprint.wild_print_backend.repository.ProductoRepositorio;

import cl.wildprint.wild_print_backend.service.ProductoServicio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de las operaciones de negocio relacionadas
 * con la administración de productos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@Transactional
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepositorio productoRepositorio;
    private final ImagenProductoRepositorio imagenProductoRepositorio;
    private final PropiedadesWildPrint propiedades;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositorio acceso a los datos de productos
     * @param imagenProductoRepositorio acceso a los datos de imágenes
     * @param propiedades propiedades personalizadas de Wild Print
     */
    public ProductoServicioImpl(
            ProductoRepositorio productoRepositorio,
            ImagenProductoRepositorio imagenProductoRepositorio,
            PropiedadesWildPrint propiedades) {
        this.productoRepositorio = productoRepositorio;
        this.imagenProductoRepositorio = imagenProductoRepositorio;
        this.propiedades = propiedades;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Producto crear(Producto producto) {
        validarPublicacion(producto);
        return productoRepositorio.save(producto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Producto actualizar(UUID id, Producto producto) {
        Producto existente = obtenerProductoActivo(id);

        existente.setNombre(producto.getNombre());
        existente.setSlug(producto.getSlug());
        existente.setDescripcionCorta(producto.getDescripcionCorta());
        existente.setDescripcion(producto.getDescripcion());
        existente.setEstado(producto.getEstado());
        existente.setDestacado(producto.isDestacado());

        if (producto.getCategorias() != null) {
            existente.setCategorias(producto.getCategorias());
        }

        validarPublicacion(existente);

        return productoRepositorio.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(UUID id) {
        return productoRepositorio.findById(id)
                .filter(producto -> producto.getFechaEliminacion() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorSlug(String slug) {
        return productoRepositorio.findBySlugAndFechaEliminacionIsNull(slug);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepositorio.findAllByFechaEliminacionIsNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarPorEstado(EstadoProducto estado) {
        return productoRepositorio.findAllByEstadoAndFechaEliminacionIsNull(estado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enviarAPapelera(UUID id) {
        Producto producto = obtenerProductoActivo(id);

        producto.setFechaEliminacion(OffsetDateTime.now());
        producto.setEstado(EstadoProducto.OCULTO);

        productoRepositorio.save(producto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Producto restaurar(UUID id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Producto no encontrado"));

        if (producto.getFechaEliminacion() == null) {
            throw new IllegalStateException(
                    "El producto no se encuentra en la papelera");
        }

        producto.setFechaEliminacion(null);
        producto.setEstado(EstadoProducto.BORRADOR);

        return productoRepositorio.save(producto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminarDefinitivamente(UUID id) {
        throw new IllegalStateException(
                "La eliminación definitiva se habilitará al integrar Cloudflare R2");
    }

    /**
     * Busca un producto que no se encuentre en papelera.
     *
     * @param id Identificador UUID del producto.
     * @return Producto encontrado.
     * @throws IllegalArgumentException Si el producto no existe o está en papelera.
     */
    private Producto obtenerProductoActivo(UUID id) {
        return productoRepositorio.findById(id)
                .filter(producto -> producto.getFechaEliminacion() == null)
                .orElseThrow(() ->
                        new IllegalArgumentException("Producto no encontrado"));
    }

    /**
     * Comprueba que un producto tenga una imagen de portada
     * antes de permitir su publicación.
     *
     * @param producto producto que se desea validar
     * @throws IllegalStateException si se intenta publicar sin portada
     */
    private void validarPublicacion(Producto producto) {

        if (producto.getEstado() == EstadoProducto.PUBLICADO
                && propiedades.isRequerirPortada()) {

            boolean tienePortada =
                    producto.getId() != null
                            && imagenProductoRepositorio
                            .existsByProducto_IdAndEsPortadaTrue(producto.getId());

            if (!tienePortada) {
                throw new IllegalStateException(
                        "No se puede publicar un producto sin imagen de portada"
                );
            }
        }
    }
}