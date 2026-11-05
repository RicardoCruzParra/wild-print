package cl.wildprint.wild_print_backend.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.OffsetDateTime;

import java.util.List;

/**
 * Gestiona globalmente las excepciones de la API.
 *
 * @author Ricardo Cruz
 * @version 1.0
 */
@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail manejarArgumentoInvalido(
            IllegalArgumentException excepcion,
            HttpServletRequest solicitud) {

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                excepcion.getMessage()
        );

        problema.setTitle("Solicitud inválida");
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());

        return problema;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail manejarErroresValidacion(
            MethodArgumentNotValidException excepcion,
            HttpServletRequest solicitud) {

        List<String> errores = excepcion.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Uno o más campos contienen errores de validación."
        );

        problema.setTitle("Error de validación");
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());
        problema.setProperty("errores", errores);

        return problema;
    }
}