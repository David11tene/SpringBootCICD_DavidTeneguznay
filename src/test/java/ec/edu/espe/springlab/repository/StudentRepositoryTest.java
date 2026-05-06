package ec.edu.espe.springlab.repository;

import ec.edu.espe.springlab.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Prueba solo la capa de datos (JPA)
public class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository; // Inyecta el repositorio

    @Test
    void shouldSaveAndFindStudentByEmail() {
        // 1. Preparar datos del estudiante
        Student student = new Student();
        student.setFullName("Test User");
        student.setEmail("test@test.com");
        student.setBirthDate(LocalDate.of(2001,12,01));
        student.setActive(true);

        // 2. Guardar el estudiante en la base de datos
        studentRepository.save(student);

        // 3. Buscar el estudiante por email
        var result = studentRepository.findByEmail("test@test.com");

        // 4. Verificar que el resultado sea correcto
        assert(result.isPresent());
        assertEquals("Test User", result.get().getFullName());
    }
}
