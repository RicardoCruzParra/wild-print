package cl.wildprint.wild_print_backend.storage.modelo;

/**
 * Representa un archivo almacenado en un proveedor externo.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public record ArchivoAlmacenado(
        String clave,
        String url
) {
}