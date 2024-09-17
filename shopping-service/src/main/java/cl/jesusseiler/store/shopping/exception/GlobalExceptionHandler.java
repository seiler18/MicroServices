package cl.jesusseiler.store.shopping.exception;

import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        String path = request.getRequestURI();
        ErrorMessage errorMessage = formatMessage(result, path);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessage> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatusCode().value())
                .error(ex.getReason())
                .code("01")
                .messages(List.of(Map.of("error", ex.getReason())))
                .path(path)
                .build();
        return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
    }

    private ErrorMessage formatMessage(BindingResult result, String path) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        
        return ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code("01")
                .messages(errors)
                .path(path)
                .build();
    }
}
