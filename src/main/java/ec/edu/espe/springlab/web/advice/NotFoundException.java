package ec.edu.espe.springlab.web.advice;

// Paso 7.a: NotFoundException -> 404 de dominio (recurso no encontrado)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
