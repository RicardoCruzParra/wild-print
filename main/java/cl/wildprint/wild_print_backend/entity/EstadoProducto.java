package cl.wildprint.wild_print_backend.entity;

/**
 * Define los estados posibles de publicación de un producto.
 * BORRADOR: producto en preparación.
 * PUBLICADO: producto visible en el catálogo público.
 * OCULTO: producto que no debe mostrarse públicamente.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public enum EstadoProducto {
    BORRADOR,
    PUBLICADO,
    OCULTO
}