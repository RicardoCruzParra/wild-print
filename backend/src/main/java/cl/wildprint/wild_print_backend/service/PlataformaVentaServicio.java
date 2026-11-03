package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.PlataformaVenta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de plataformas de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface PlataformaVentaServicio {

    /**
     * Registra una nueva plataforma de venta.
     *
     * @param plataforma plataforma que se desea registrar
     * @return plataforma creada
     */
    PlataformaVenta crear(PlataformaVenta plataforma);

    /**
     * Actualiza los datos de una plataforma existente.
     *
     * @param id identificador de la plataforma
     * @param plataforma datos actualizados
     * @return plataforma actualizada
     */
    PlataformaVenta actualizar(UUID id, PlataformaVenta plataforma);

    /**
     * Busca una plataforma por su identificador.
     *
     * @param id identificador de la plataforma
     * @return plataforma encontrada, si existe
     */
    Optional<PlataformaVenta> buscarPorId(UUID id);

    /**
     * Busca una plataforma mediante su código.
     *
     * @param codigo código único de la plataforma
     * @return plataforma encontrada, si existe
     */
    Optional<PlataformaVenta> buscarPorCodigo(String codigo);

    /**
     * Obtiene todas las plataformas registradas.
     *
     * @return lista de plataformas
     */
    List<PlataformaVenta> listarTodas();

    /**
     * Obtiene las plataformas que se encuentran activas.
     *
     * @return lista de plataformas activas
     */
    List<PlataformaVenta> listarActivas();

    /**
     * Cambia el estado de disponibilidad de una plataforma.
     *
     * @param id identificador de la plataforma
     * @param activa nuevo estado de disponibilidad
     * @return plataforma actualizada
     */
    PlataformaVenta cambiarEstado(UUID id, boolean activa);
}