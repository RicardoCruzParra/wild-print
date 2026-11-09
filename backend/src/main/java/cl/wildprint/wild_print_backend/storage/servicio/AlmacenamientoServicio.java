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

    ArchivoAlmacenado almacenar(MultipartFile archivo);

    void eliminar(String clave);
}