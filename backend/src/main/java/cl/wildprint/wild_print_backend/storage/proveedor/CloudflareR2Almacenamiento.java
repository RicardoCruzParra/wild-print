package cl.wildprint.wild_print_backend.storage.proveedor;

import cl.wildprint.wild_print_backend.common.OperacionNoPermitidaException;
import cl.wildprint.wild_print_backend.config.PropiedadesWildPrint;
import cl.wildprint.wild_print_backend.storage.modelo.ArchivoAlmacenado;
import cl.wildprint.wild_print_backend.storage.servicio.AlmacenamientoServicio;
import cl.wildprint.wild_print_backend.storage.validacion.ValidadorArchivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.IOException;
import java.util.UUID;

/**
 * Implementa el almacenamiento de archivos utilizando Cloudflare R2.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "wild-print",
        name = "almacenamiento",
        havingValue = "r2"
)
public class CloudflareR2Almacenamiento implements AlmacenamientoServicio {

    private final PropiedadesWildPrint propiedades;
    private final ValidadorArchivo validadorArchivo;
    private final S3Client s3Client;

    /**
     * Almacena un archivo en Cloudflare R2.
     *
     * @param archivo archivo que se desea almacenar
     * @return información del archivo almacenado
     */
    @Override
    public ArchivoAlmacenado almacenar(MultipartFile archivo){

        validadorArchivo.validar(archivo);
        String clave = UUID.randomUUID() + obtenerExtension(archivo);
        try {
            PutObjectRequest solicitud = PutObjectRequest.builder()
                    .bucket(propiedades.getBucket())
                    .key(clave)
                    .contentType(archivo.getContentType())
                    .contentLength(archivo.getSize())
                    .build();

            s3Client.putObject(
                    solicitud,
                    RequestBody.fromInputStream(
                            archivo.getInputStream(),
                            archivo.getSize()
                    )
            );

        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el archivo para almacenarlo.", e);
        }
        return new ArchivoAlmacenado(
                clave,
                propiedades.getUrlPublica() + "/" + clave
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
            throw new OperacionNoPermitidaException("El archivo no tiene una extensión válida.");
        }

        return nombreOriginal.substring(nombreOriginal.lastIndexOf(".")).toLowerCase();
    }

    /**
     * Elimina un archivo de Cloudflare R2.
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

        DeleteObjectRequest solicitud = DeleteObjectRequest.builder()
                .bucket(propiedades.getBucket())
                .key(clave)
                .build();

        s3Client.deleteObject(solicitud);
    }
}