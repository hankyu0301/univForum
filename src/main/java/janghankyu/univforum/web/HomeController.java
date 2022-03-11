package janghankyu.univforum.web;

import janghankyu.univforum.web.login.LoginForm;
import janghankyu.univforum.web.login.annotation.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login LoginForm loginForm) {
        log.info("loginForm : {}", loginForm);
        if(loginForm == null) {
            return "home";
        }
        return "redirect:/main";
    }
}
