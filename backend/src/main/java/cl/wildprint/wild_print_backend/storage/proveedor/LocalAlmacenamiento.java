package cl.wildprint.wild_print_backend.storage.proveedor;

import cl.wildprint.wild_print_backend.common.OperacionNoPermitidaException;
import cl.wildprint.wild_print_backend.config.PropiedadesWildPrint;
import cl.wildprint.wild_print_backend.storage.modelo.ArchivoAlmacenado;
import cl.wildprint.wild_print_backend.storage.servicio.AlmacenamientoServicio;
import cl.wildprint.wild_print_backend.storage.validacion.ValidadorArchivo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

/**
 * Implementa el almacenamiento de archivos en el sistema local.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "wild-print",
        name = "almacenamiento",
        havingValue = "local"
)
public class LocalAlmacenamiento implements AlmacenamientoServicio {

    private final PropiedadesWildPrint propiedades;
    private final ValidadorArchivo validadorArchivo;

    /**
     * Almacena un archivo en el sistema local.
     *
     * @param archivo archivo que se desea almacenar
     * @return información del archivo almacenado
     */
    @Override
    public ArchivoAlmacenado almacenar(MultipartFile archivo) {

        validadorArchivo.validar(archivo);

        String clave = UUID.randomUUID() + obtenerExtension(archivo);
        Path directorio = Path.of(propiedades.getRutaLocal())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(directorio);

            Path destino = directorio.resolve(clave);
            Files.copy(archivo.getInputStream(), destino);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo almacenar el archivo localmente.", e
            );
        }

        return new ArchivoAlmacenado(
                clave,
                "/archivos/" + clave
        );
    }

    /**
     * Obtiene la extensión del archivo original.
     *
     * @param archivo archivo del que se obtiene la extensión
     * @return extensión del archivo
     */
    private String obtenerExtension(MultipartFile archivo) {

        String nombreOriginal = archivo.getOriginalFilename();

        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            throw new OperacionNoPermitidaException(
                    "El archivo no tiene una extensión válida."
            );
        }

        return nombreOriginal
                .substring(nombreOriginal.lastIndexOf("."))
                .toLowerCase(Locale.ROOT);
    }

    /**
     * Elimina un archivo del almacenamiento local.
     *
     * @param clave identificador del archivo
     */
    @Override
    public void eliminar(String clave) {

        if (clave == null || clave.isBlank()) {
            throw new OperacionNoPermitidaException(
                    "La clave del archivo no puede estar vacía."
            );
        }

        Path directorio = Path.of(propiedades.getRutaLocal())
                .toAbsolutePath()
                .normalize();

        Path archivo = directorio.resolve(clave).normalize();

        if (!archivo.startsWith(directorio)) {
            throw new OperacionNoPermitidaException(
                    "La ruta del archivo no es válida."
            );
        }

        try {
            Files.deleteIfExists(archivo);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo eliminar el archivo local.", e
            );
        }
    }
}
