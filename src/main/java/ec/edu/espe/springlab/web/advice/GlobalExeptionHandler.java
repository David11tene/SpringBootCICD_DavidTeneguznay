package ec.edu.espe.springlab.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Paso 7: Implementar excepciones y manejo global (ControllerAdvice)
// Paso 7.b: Manejo global de excepciones
@ControllerAdvice
public class GlobalExeptionHandler {
    // Paso 7.b: 404 de negocio
    @ExceptionHandler(value=NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex){
        return error(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    // Paso 7.b: 409 de negocio (duplicados, etc.).
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException ex){
            return error(HttpStatus.CONFLICT,ex.getMessage());
    }

    // Paso 7.b: 400 por validaciones fallidas de @Valid en los DTOs.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        Map<String,String> errors = new HashMap<>();
        for(FieldError fe : ex.getBindingResult().getFieldErrors()){
            errors.put(fe.getField(),fe.getDefaultMessage());
        }

        body.put("errors", errors.toString());
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    // Paso 7.b: 500 genérica (última red de seguridad).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor: " + ex.getMessage());
    }

    // Paso 7.b: Construir un JSON estándar de error.
    private ResponseEntity<Map<String,Object>> error(HttpStatus status, String message){
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status",status.value());
        body.put("error", message);
        return new ResponseEntity<>(body,status);
    }
}
