package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    //Crear un estudiante
    StudentResponse create(StudentCreateRequest request);

    //Buscar estudiante por ID
    StudentResponse getById(Long id);

    //Listar todos los estudiantes
    List<StudentResponse> list();

    // Listar con paginación y búsqueda
    Page<StudentResponse> listPaged(String name, Pageable pageable);

    // Actualizar estudiante
    StudentResponse update(Long id, StudentCreateRequest.Update request);

    //Cambiar el estado
    StudentResponse desactivate(Long id);
}
