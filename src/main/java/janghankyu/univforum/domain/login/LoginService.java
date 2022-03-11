package janghankyu.univforum.domain.login;

import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.web.login.LoginForm;

public interface LoginService {
    Student login(LoginForm loginForm);
}
