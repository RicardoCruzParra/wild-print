package cl.wildprint.wild_print_backend.storage.validacion;

import cl.wildprint.wild_print_backend.common.OperacionNoPermitidaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Válida los archivos que ingresan al sistema.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
public class ValidadorArchivo {

    private static final long TAMANO_MAXIMO = 5 * 1024 * 1024;

    private static final Set<String> TIPOS_PERMITIDOS = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    public void validar(MultipartFile archivo) {

        if (archivo == null || archivo.isEmpty()) {
            throw new OperacionNoPermitidaException(
                    "El archivo no puede estar vacío."
            );
        }

        if (archivo.getSize() > TAMANO_MAXIMO) {
            throw new OperacionNoPermitidaException(
                    "El archivo supera el tamaño máximo permitido de 5 MB."
            );
        }

        if (!TIPOS_PERMITIDOS.contains(archivo.getContentType())) {
            throw new OperacionNoPermitidaException(
                    "El formato del archivo no está permitido."
            );
        }
    }
}