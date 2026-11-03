package cl.wildprint.wild_print_backend.repository;

import cl.wildprint.wild_print_backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio para las operaciones de persistencia de categorías.
 * Permite administrar y consultar las categorías utilizadas
 * para clasificar los productos del catálogo.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface CategoriaRepositorio extends JpaRepository<Categoria, UUID>{
}
