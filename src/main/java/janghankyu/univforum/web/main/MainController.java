package janghankyu.univforum.web.main;

import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.domain.student.StudentRepository;
import janghankyu.univforum.domain.student.StudentService;
import janghankyu.univforum.web.login.LoginForm;
import janghankyu.univforum.web.login.annotation.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @GetMapping
    public String main(@Login LoginForm loginForm, Model model) {
        log.info("login");
        Optional<Student> loginStudent = studentService.findStudent(loginForm.getEmail());
        log.info("loginStudent : {}", loginStudent);
        model.addAttribute("loginStudent", loginStudent.get());
        return "main";
    }

    @GetMapping("/student")
    public String showStudent(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "search";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        log.info("logout");
        HttpSession session = req.getSession(false);
        if( session != null ){
            session.invalidate();
        }
        return "redirect:/";
    }
}
