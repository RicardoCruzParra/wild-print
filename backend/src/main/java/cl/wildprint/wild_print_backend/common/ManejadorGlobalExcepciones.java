package cl.wildprint.wild_print_backend.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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

    /**
     * Maneja los errores de argumentos inválidos.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail manejarArgumentoInvalido(
            IllegalArgumentException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.BAD_REQUEST,
                "Solicitud inválida",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los errores de validación de los DTO.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema con la lista de errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail manejarErroresValidacion(
            MethodArgumentNotValidException excepcion,
            HttpServletRequest solicitud) {

        List<String> errores = excepcion.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .toList();

        ProblemDetail problema = construirProblema(
                HttpStatus.BAD_REQUEST,
                "Error de validación",
                "Uno o más campos contienen errores de validación.",
                solicitud
        );

        problema.setProperty("errores", errores);

        return problema;
    }

    /**
     * Maneja las operaciones que incumplen una regla de negocio.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ProblemDetail manejarOperacionNoPermitida(
            OperacionNoPermitidaException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Operación no permitida",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los recursos que no existen.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail manejarRecursoNoEncontrado(
            RecursoNoEncontradoException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.NOT_FOUND,
                "Recurso no encontrado",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los recursos duplicados.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ProblemDetail manejarRecursoDuplicado(
            RecursoDuplicadoException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.CONFLICT,
                "Recurso duplicado",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los estados inválidos de una operación.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail manejarEstadoInvalido(
            IllegalStateException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.CONFLICT,
                "Operación no válida",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los errores de formato en los parámetros de la solicitud.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            HttpMessageNotReadableException.class
    })
    public ProblemDetail manejarSolicitudInvalida(
            Exception excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.BAD_REQUEST,
                "Solicitud inválida",
                excepcion.getMessage(),
                solicitud
        );
    }

    /**
     * Maneja los archivos que superan el tamaño máximo permitido.
     *
     * @param excepcion excepción producida
     * @param solicitud solicitud HTTP
     * @return detalle del problema
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ProblemDetail manejarArchivoDemasiadoGrande(
            MaxUploadSizeExceededException excepcion,
            HttpServletRequest solicitud) {

        return construirProblema(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "Archivo demasiado grande",
                "El archivo supera el tamaño máximo permitido de 5 MB.",
                solicitud
        );
    }

    /**
     * Construye un ProblemDetail con los datos comunes de la API.
     *
     * @param estado estado HTTP
     * @param titulo título del problema
     * @param detalle detalle del problema
     * @param solicitud solicitud HTTP
     * @return problema construido
     */
    private ProblemDetail construirProblema(
            HttpStatus estado,
            String titulo,
            String detalle,
            HttpServletRequest solicitud) {

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(estado, detalle);

        problema.setTitle(titulo);
        problema.setProperty("fecha", OffsetDateTime.now());
        problema.setProperty("ruta", solicitud.getRequestURI());

        return problema;
    }
}