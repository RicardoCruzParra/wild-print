package cl.wildprint.wild_print_backend.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Excepción utilizada cuando un recurso solicitado no existe.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail manejarRecursoNoEncontrado(
            RecursoNoEncontradoException excepcion,
            HttpServletRequest solicitud) {

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage()
        );

        problema.setTitle("Recurso no encontrado");
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());

        return problema;
    }
}