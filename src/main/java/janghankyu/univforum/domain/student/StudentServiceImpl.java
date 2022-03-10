package janghankyu.univforum.domain.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public Student join(Student student) {
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findStudent(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(()->new IllegalArgumentException("해당 학생은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Optional<Student> findStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }
}
