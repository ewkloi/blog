package cn.edu.controller.admin;

import cn.edu.po.User;
import cn.edu.service.UserService;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class UserController {

  @Autowired
  private UserService userService;

  @Value("${pageRequest.pagesize}")
  private int pageSize;

  @GetMapping
  public String loginPage(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (null != user && user.getType() == 1) {
      return "redirect:/admin/blogs";
    }
    //跳转到登录页面
    return "admin/login";
  }

  @PostMapping("/login")
  public String login(@RequestParam String username, @RequestParam String password,
      HttpSession httpSession, RedirectAttributes attributes) {
    if (username == null || password == null) {
      attributes.addFlashAttribute("message", "用户名或密码不能为空");
      return "redirect:/admin";
    }
    User user = userService.checkUser(username, password);
    if (user != null && user.getType() == 1) {
      user.setPassword(null);
      httpSession.setAttribute("user", user);
      return "redirect:/admin/blogs";
    } else {
      attributes.addFlashAttribute("message", "用户名或密码错误");
      return "redirect:/admin";
    }
  }

  @GetMapping("/layout")
  public String logout(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/admin";
  }

  @GetMapping("/users")
  public String users(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      Model model) {
    PageInfo<User> pageInfo = userService.list(pageNum, pageSize);
    model.addAttribute("page", pageInfo);
    return "/admin/users";
  }

  @GetMapping("/users/{id}/delete")
  public String delete(@PathVariable String id, RedirectAttributes attributes) {
    try {
       userService.delete(id);
      attributes.addFlashAttribute("message", "删除成功");
    }catch (Exception e){
      attributes.addFlashAttribute("message", "删除失败");
    }
    return "redirect:/admin/users";
  }
}
