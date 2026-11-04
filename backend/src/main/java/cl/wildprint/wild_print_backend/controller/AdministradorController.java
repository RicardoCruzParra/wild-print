
package cl.wildprint.wild_print_backend.controller;

import cl.wildprint.wild_print_backend.dto.administrador.AdministradorDTO;
import cl.wildprint.wild_print_backend.mapper.AdministradorMapper;
import cl.wildprint.wild_print_backend.service.UsuarioAdministradorServicio;
import cl.wildprint.wild_print_backend.dto.administrador.CrearAdministradorDTO;
import cl.wildprint.wild_print_backend.entity.UsuarioAdministrador;
import cl.wildprint.wild_print_backend.dto.administrador.ActualizarAdministradorDTO;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * Expone los endpoints de consulta y administración
 * de usuarios administradores.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestController
@RequestMapping("/api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final UsuarioAdministradorServicio usuarioAdministradorServicio;
    private final AdministradorMapper administradorMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios administradores registrados.
     *
     * @return lista de administradores
     */
    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> listarAdministradores() {

        List<AdministradorDTO> administradores =
                usuarioAdministradorServicio.listarTodos()
                        .stream()
                        .map(administradorMapper::aDTO)
                        .toList();

        return ResponseEntity.ok(administradores);
    }

    /**
     * Registra un nuevo usuario administrador.
     *
     * @param dto datos del administrador que se desea crear
     * @return administrador creado
     */
    @PostMapping
    public ResponseEntity<AdministradorDTO> crearAdministrador(
            @Valid @RequestBody CrearAdministradorDTO dto
    ) {

        UsuarioAdministrador administrador =
                administradorMapper.desdeCrearDTO(dto);

        administrador.setClaveHash(
                passwordEncoder.encode(dto.getClave())
        );

        UsuarioAdministrador administradorCreado =
                usuarioAdministradorServicio.crear(administrador);

        return ResponseEntity.status(201)
                .body(administradorMapper.aDTO(administradorCreado));
    }

    /**
     * Actualiza los datos de un usuario administrador.
     *
     * @param id identificador UUID del administrador
     * @param dto datos actualizados
     * @return administrador actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorDTO> actualizarAdministrador(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarAdministradorDTO dto
    ) {

        UsuarioAdministrador administrador =
                usuarioAdministradorServicio.buscarPorId(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe el administrador indicado"
                        ));

        administradorMapper.actualizarDesdeDTO(dto, administrador);

        UsuarioAdministrador administradorActualizado =
                usuarioAdministradorServicio.actualizar(id, administrador);

        return ResponseEntity.ok(
                administradorMapper.aDTO(administradorActualizado)
        );
    }

    /**
     * Cambia el estado de activación de un usuario administrador.
     *
     * @param id identificador UUID del administrador
     * @param activo nuevo estado de activación
     * @return administrador actualizado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<AdministradorDTO> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam boolean activo
    ) {

        UsuarioAdministrador administradorActualizado =
                usuarioAdministradorServicio.cambiarEstado(id, activo);

        return ResponseEntity.ok(
                administradorMapper.aDTO(administradorActualizado)
        );
    }
}
