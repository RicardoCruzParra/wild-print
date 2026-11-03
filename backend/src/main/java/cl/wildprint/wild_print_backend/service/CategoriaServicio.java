package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de categorías.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface CategoriaServicio {

    /**
     * Crea una nueva categoría.
     *
     * @param categoria categoría que se desea registrar
     * @return categoría creada
     */
    Categoria crear(Categoria categoria);

    /**
     * Actualiza los datos de una categoría existente.
     *
     * @param id identificador de la categoría
     * @param categoria datos actualizados
     * @return categoría actualizada
     */
    Categoria actualizar(UUID id, Categoria categoria);

    /**
     * Busca una categoría por su identificador.
     *
     * @param id identificador de la categoría
     * @return categoría encontrada, si existe
     */
    Optional<Categoria> buscarPorId(UUID id);

    /**
     * Busca una categoría por su slug.
     *
     * @param slug identificador textual de la categoría
     * @return categoría encontrada, si existe
     */
    Optional<Categoria> buscarPorSlug(String slug);

    /**
     * Obtiene todas las categorías registradas.
     *
     * @return lista de categorías
     */
    List<Categoria> listarTodas();
}
