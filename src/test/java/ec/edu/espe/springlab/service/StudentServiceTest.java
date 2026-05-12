package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.impl.StudentServiceImpl;
import ec.edu.espe.springlab.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({StudentServiceImpl.class})
public class StudentServiceTest {
    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicatedEmail() {
        // 1. Registrar un estudiante previo en la base de datos
        Student existing = new Student();
        existing.setFullName("Existing");
        existing.setEmail("duplicated@duplicated.com");
        existing.setBirthDate(LocalDate.of(2004,10,12));
        existing.setActive(true);
        repository.save(existing);

        // 2. Crear una solicitud con el mismo correo electronico
        StudentCreateRequest req =  new StudentCreateRequest();
        req.setFullName("New user");
        req.setEmail("duplicated@duplicated.com");
        existing.setBirthDate(LocalDate.of(2004,10,12));

        // 3. Verificar que el servicio lance ConflictException al intentar crear el duplicado
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ConflictException.class);
    }
}
