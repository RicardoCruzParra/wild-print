 package cl.wildprint.wild_print_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Gestiona las propiedades personalizadas de Wild Print.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "wild-print")
@Getter
@Setter
public class PropiedadesWildPrint {

    private String almacenamiento;
    private String endpoint;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String urlPublica;
    private String rutaLocal;
}