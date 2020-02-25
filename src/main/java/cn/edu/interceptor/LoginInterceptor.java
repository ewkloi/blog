package cn.edu.interceptor;


import cn.edu.po.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    User user = (User) request.getSession().getAttribute("user");
    if (null == user || user.getType()==0) {
      response.sendRedirect("/admin");
      return false;
    }
    return true;
  }
}
