package ec.edu.espe.springlab.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

// Crear una entidad de negocio persistente que mapea la tabla "students".
@Entity
@Table(name="student")
public class Student {

    //cada atributo será una columna.
       // Clave primaria con autoincremento.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre obligatorio, longitud máx. 120
    @Column(nullable = false, length = 120)
    private String fullName;

    // Email obligatorio y único (restricción a nivel BD)
    @Column(nullable = false,unique = true, length = 120)
    private String email;

    //Fecha de nacimiento (opcional)
    private LocalDate birthDate;

    // Estado lógico del estudiante. Por defecto, true
    private  boolean active = true ;

    //Constructor vacío.
    public Student() {
    }

    //Getters y setters.
    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
