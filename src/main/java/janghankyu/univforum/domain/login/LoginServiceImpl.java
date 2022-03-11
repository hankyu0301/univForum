package janghankyu.univforum.domain.login;

import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.domain.student.StudentRepository;
import janghankyu.univforum.web.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService{

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public Student login(LoginForm loginForm) {
        return studentRepository.findByEmail(loginForm.getEmail())
                .filter(student -> student.getPassword().equals(loginForm.getPassword()))
                .orElse(null);
    }
}
