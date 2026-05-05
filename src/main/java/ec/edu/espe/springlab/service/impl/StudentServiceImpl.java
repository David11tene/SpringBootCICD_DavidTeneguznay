package ec.edu.espe.springlab.service.impl;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.StudentService;
import ec.edu.espe.springlab.web.advice.ConflictException;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    //Inyeccion de dependencia
    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public StudentResponse create(StudentCreateRequest request) {
        if (repo.existsByEmail(request.getEmail())){
            throw new ConflictException("Email ya registrado");
        }
        Student s = new Student();
        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setBirthDate(request.getBirthDate());
        s.setActive(true);
        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse getById(Long id) {
        Student s = repo.findById(id).orElseThrow(()->
                new NotFoundException("Estudiante no encontrado"));
        return toResponse(s);
    }

    @Override
    public List<StudentResponse> list() {
        return
                repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public Page<StudentResponse> listPaged(String name, Pageable pageable) {
        Page<Student> students;
        if (name != null && !name.isEmpty()) {
            students = repo.findByFullNameContainingIgnoreCase(name, pageable);
        } else {
            students = repo.findAll(pageable);
        }
        return students.map(this::toResponse);
    }

    @Override
    public StudentResponse update(Long id, StudentCreateRequest.Update request) {
        Student s = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Estudiante no encontrado"));

        if (request.getFullName() != null) s.setFullName(request.getFullName());
        if (request.getEmail() != null) {
            if (!s.getEmail().equals(request.getEmail()) && repo.existsByEmail
                    (request.getEmail())) {
                throw new ConflictException("Email ya registrado");
            }
            s.setEmail(request.getEmail());
        }
        if (request.getBirthDate() != null) s.setBirthDate(request.getBirthDate());
        if (request.getActive() != null) s.setActive(request.getActive());

        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse desactivate(Long id) {
        Student s = repo.findById(id).orElseThrow(()->
        new NotFoundException("Estudiante no encontrado"));
        s.setActive(false);
        return   toResponse(repo.save(s));
    }

    private StudentResponse toResponse(Student s){
        StudentResponse r = new StudentResponse();
        r.setId(s.getId());
        r.setFullName(s.getFullName());
        r.setEmail(s.getEmail());
        r.setBirthDate(s.getBirthDate());
        r.setActive(s.isActive());
        return r;
    }
}
