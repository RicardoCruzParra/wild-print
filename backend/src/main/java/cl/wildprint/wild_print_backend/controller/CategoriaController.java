package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.categoria.ActualizarCategoriaDTO;
import cl.wildprint.wild_print_backend.dto.categoria.CategoriaDTO;
import cl.wildprint.wild_print_backend.dto.categoria.CrearCategoriaDTO;
import cl.wildprint.wild_print_backend.entity.Categoria;
import cl.wildprint.wild_print_backend.mapper.CategoriaMapper;
import cl.wildprint.wild_print_backend.service.CategoriaServicio;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración de categorías.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaServicio categoriaServicio;
    private final CategoriaMapper categoriaMapper;

    /**
     * Obtiene todas las categorías registradas.
     *
     * @return lista de categorías
     */
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {

        List<CategoriaDTO> categorias = categoriaServicio.listarTodas()
                .stream()
                .map(categoriaMapper::aDTO)
                .toList();

        return ResponseEntity.ok(categorias);
    }

    /**
     * Registra una nueva categoría.
     *
     * @param dto datos de la categoría que se desea crear
     * @return categoría creada
     */
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(
            @Valid @RequestBody CrearCategoriaDTO dto
    ) {

        Categoria categoria = categoriaMapper.desdeCrearDTO(dto);

        Categoria categoriaCreada = categoriaServicio.crear(categoria);

        return ResponseEntity.status(201)
                .body(categoriaMapper.aDTO(categoriaCreada));
    }


    /**
     * Actualiza los datos de una categoría existente.
     *
     * @param id identificador UUID de la categoría
     * @param dto datos actualizados
     * @return categoría actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarCategoriaDTO dto
    ) {

        Categoria categoria = categoriaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la categoría indicada"
                ));

        categoriaMapper.actualizarDesdeDTO(dto, categoria);

        Categoria categoriaActualizada =
                categoriaServicio.actualizar(id, categoria);

        return ResponseEntity.ok(
                categoriaMapper.aDTO(categoriaActualizada)
        );
    }


    /**
     * Busca una categoría por su identificador.
     *
     * @param id identificador UUID de la categoría
     * @return categoría encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(
            @PathVariable UUID id
    ) {

        Categoria categoria = categoriaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la categoría indicada"
                ));

        return ResponseEntity.ok(categoriaMapper.aDTO(categoria));
    }


    /**
     * Busca una categoría por su slug.
     *
     * @param slug identificador textual de la categoría
     * @return categoría encontrada
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorSlug(
            @PathVariable String slug
    ) {

        Categoria categoria = categoriaServicio.buscarPorSlug(slug)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la categoría indicada"
                ));

        return ResponseEntity.ok(categoriaMapper.aDTO(categoria));
    }
}
