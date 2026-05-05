package ec.edu.espe.springlab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
// Marca la clase como un componente de Spring (bean gestionado automaticamente)
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    // Token "simulado" (hardcodeado) para validacion basica
    // Incluye el prefijo "Bearer " como se usa en el header Authorization
    private static final String SECRET_TOKEN = "Bearer springlab-secure-token-2026";

    // Metodo que se ejecuta ANTES de que la peticion llegue al controlador
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
                             Object handler) throws Exception {

        // Guarda el tiempo actual en milisegundos dentro de la request
        // Se usara despues para calcular el tiempo total de la peticion
        req.setAttribute("t0", System.currentTimeMillis());

        // Imprime en consola el metodo HTTP (GET, POST, etc.) y la URI solicitada
        System.out.println("preHandle: " + req.getMethod() + " " + req.getRequestURI() );

        // Obtiene el header "Authorization" de la peticion HTTP
        String authHeader = req.getHeader("Authorization");

        // Verifica si el header no existe o no tiene el formato "Bearer ..."
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"Falta el token de autorizacion\"}");
            resp.setContentType("application/json");
            return false;
        }

        // Verificacion basica del token (simulada)
        if (!authHeader.equals(SECRET_TOKEN)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"Token invalido o expirado\"}");
            resp.setContentType("application/json");
            return false;
        }

        // Si todas las validaciones pasan, permite que la peticion continue
        return true;
    }

    // Metodo que se ejecuta AL FINAL de la peticion (despues del controlador y la respuesta)
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex){

        // Recupera el tiempo inicial guardado en preHandle
        Long t0 = (Long)req.getAttribute("t0");

        // Calcula el tiempo total de ejecucion
        // Si no existe t0, devuelve -1
        long elapsed = (t0 == null) ? -1 : (System.currentTimeMillis() - t0);

        // Imprime en consola el status HTTP de la respuesta y el tiempo total en ms
        System.out.println("afterCompletion -> status:" + resp.getStatus() + " tiempo: " + elapsed + "ms");
    }
}