package cl.wildprint.wild_print_backend.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Excepción utilizada cuando se intenta registrar un recurso duplicado.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public class RecursoDuplicadoException extends RuntimeException {

    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ProblemDetail manejarRecursoDuplicado(
            RecursoDuplicadoException excepcion,
            HttpServletRequest solicitud) {

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                excepcion.getMessage()
        );

        problema.setTitle("Recurso duplicado");
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());

        return problema;
    }
}