package cl.wildprint.wild_print_backend.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Excepción utilizada cuando una operación incumple una regla de negocio.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
public class OperacionNoPermitidaException extends RuntimeException {

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }

    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ProblemDetail manejarOperacionNoPermitida(
            OperacionNoPermitidaException excepcion,
            HttpServletRequest solicitud) {

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.valueOf(422),
                excepcion.getMessage()
        );

        problema.setTitle("Operación no permitida");
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());

        return problema;
    }
}