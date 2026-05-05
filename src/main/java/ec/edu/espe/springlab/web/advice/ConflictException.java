package ec.edu.espe.springlab.web.advice;

// Paso 7.a: RuntimeException -> 409 de dominio (conflicto de negocio, p.ej., email duplicado)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
