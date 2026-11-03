package cl.wildprint.wild_print_backend.service.impl;

import cl.wildprint.wild_print_backend.entity.Categoria;
import cl.wildprint.wild_print_backend.repository.CategoriaRepositorio;
import cl.wildprint.wild_print_backend.service.CategoriaServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementa las operaciones de administración de categorías.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServicioImpl implements CategoriaServicio {

    /**
     * Repositorio encargado del acceso a las categorías.
     */
    private final CategoriaRepositorio categoriaRepositorio;

    /**
     * {@inheritDoc}
     */
    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Categoria actualizar(UUID id, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la categoría con identificador: " + id));

        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setSlug(categoria.getSlug());
        categoriaExistente.setDescripcion(categoria.getDescripcion());

        return categoriaRepositorio.save(categoriaExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorId(UUID id) {
        return categoriaRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorSlug(String slug) {
        return categoriaRepositorio.findBySlug(slug);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepositorio.findAll();
    }
}