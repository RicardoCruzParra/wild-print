package cl.wildprint.wild_print_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

/**
 * Configura el acceso HTTP a los archivos almacenados localmente.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "wild-print",
        name = "almacenamiento",
        havingValue = "local"
)
public class AlmacenamientoWebConfiguracion implements WebMvcConfigurer {

    private final PropiedadesWildPrint propiedades;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path directorio = Path.of(propiedades.getRutaLocal())
                .toAbsolutePath()
                .normalize();

        registry.addResourceHandler("/archivos/**")
                .addResourceLocations(directorio.toUri().toString());
    }
}
