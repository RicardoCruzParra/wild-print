package cl.wildprint.wild_print_backend.storage.servicio;

import cl.wildprint.wild_print_backend.storage.modelo.ArchivoAlmacenado;
import org.springframework.web.multipart.MultipartFile;

/**
 * Define las operaciones disponibles para almacenar archivos.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public interface AlmacenamientoServicio {

    /**
     * Almacena un archivo en el proveedor configurado.
     *
     * @param archivo archivo que se desea almacenar
     * @return información del archivo almacenado
     */
    ArchivoAlmacenado almacenar(MultipartFile archivo);

    /**
     * Construye la URL pública de un archivo almacenado.
     *
     * @param clave identificador del archivo
     * @return URL pública del archivo
     */
    String obtenerUrl(String clave);

    /**
     * Elimina un archivo del proveedor configurado.
     *
     * @param clave identificador del archivo
     */
    void eliminar(String clave);
}