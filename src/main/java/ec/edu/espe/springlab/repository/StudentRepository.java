package ec.edu.espe.springlab.repository;

import ec.edu.espe.springlab.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //Buscar un estudiante por email
    Optional<Student> findByEmail(String email);

    //respuesta si existe al menos un registro
    boolean existsByEmail(String email);

    // Buscar por nombre con paginación
    Page<Student> findByFullNameContainingIgnoreCase
    (String fullName, Pageable pageable);
}
