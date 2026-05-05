package ec.edu.espe.springlab.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
//DTO de ENTRADA para crear estudiantes.
public class StudentCreateRequest {
    // Nombre requerido: mínimo 3, máximo 120 caracteres.
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El nombre solo debe contener letras y espacios")
    private String fullName;

    //Email requerido, formato válido y con límite de tamaño.
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    @Size(max =120)
    private String email;

    //Fecha de nacimiento (opcional)
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    // getters y setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static class Update {
        @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y " +
                "120 caracteres")
        @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El nombre solo debe " +
                "contener letras y espacios")
        private String fullName;

        @Email(message = "El formato del email no es válido")
        @Size(max = 120)
        private String email;

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate birthDate;

        private Boolean active;

        // getters y setters
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
    }
}
