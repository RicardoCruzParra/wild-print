package cl.wildprint.wild_print_backend.service;

import cl.wildprint.wild_print_backend.entity.EnlaceVenta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define las operaciones de administración de enlaces de venta.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface EnlaceVentaServicio {

    /**
     * Registra un nuevo enlace de venta.
     *
     * @param enlace enlace que se desea registrar
     * @return enlace creado
     */
    EnlaceVenta crear(EnlaceVenta enlace);

    /**
     * Actualiza los datos de un enlace existente.
     *
     * @param id identificador del enlace
     * @param enlace datos actualizados
     * @return enlace actualizado
     */
    EnlaceVenta actualizar(UUID id, EnlaceVenta enlace);

    /**
     * Busca un enlace mediante su identificador.
     *
     * @param id identificador del enlace
     * @return enlace encontrado, si existe
     */
    Optional<EnlaceVenta> buscarPorId(UUID id);

    /**
     * Obtiene los enlaces asociados a un producto.
     *
     * @param productoId identificador del producto
     * @return lista de enlaces del producto
     */
    List<EnlaceVenta> listarPorProducto(UUID productoId);

    /**
     * Obtiene todos los enlaces registrados.
     *
     * @return lista de enlaces
     */
    List<EnlaceVenta> listarTodos();

    /**
     * Cambia el estado de disponibilidad de un enlace.
     *
     * @param id identificador del enlace
     * @param activo nuevo estado del enlace
     * @return enlace actualizado
     */
    EnlaceVenta cambiarEstado(UUID id, boolean activo);

    /**
     * Elimina un enlace de venta.
     *
     * @param id identificador del enlace
     */
    void eliminar(UUID id);
}