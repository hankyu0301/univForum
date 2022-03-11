package janghankyu.univforum.web.interceptor;

import janghankyu.univforum.web.session.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_STUDENT)==null){
            response.sendRedirect("/login?redirectURL="+request.getRequestURI());
            return false;
        }
        return true;
    }
}
