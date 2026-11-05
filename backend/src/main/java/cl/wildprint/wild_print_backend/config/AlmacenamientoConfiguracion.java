package cl.wildprint.wild_print_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.net.URI;

/**
 * Configura el cliente S3 para la conexión con Cloudflare R2.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@ConditionalOnProperty(
        prefix = "wild-print",
        name = "almacenamiento",
        havingValue = "r2"
)
@Configuration
public class AlmacenamientoConfiguracion {

    @Bean
    public S3Client s3Client(PropiedadesWildPrint propiedades) {

        return S3Client.builder()
                .endpointOverride(URI.create(propiedades.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        propiedades.getAccessKey(),
                                        propiedades.getSecretKey()
                                )
                        )
                )
                .region(Region.of("auto"))
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build()
                )
                .build();
    }
}