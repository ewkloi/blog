package cn.edu.controller.user;

import cn.edu.po.User;
import cn.edu.service.UserService;
import cn.edu.utils.Md5Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserLoginController {
  @Value("${default.avatar}")
  private String avatar;
  @Autowired
  private UserService userService;
  @PostMapping("/login")
  public String login(@RequestParam String username,@RequestParam String password, RedirectAttributes attributes, HttpSession session,
      Model model){
    User u=new User();
    u.setUsername(username);
    u.setPassword(password);
    model.addAttribute("user",u);
    if (username==null || password==null){
      attributes.addFlashAttribute("message","用户名或密码不能为空");
      return "redirect:login";
    }
    User user=userService.checkUser(username, password);
    if(user!=null){
      user.setPassword(null);
      session.setAttribute("user",user);
      return "redirect:/";
    }else{
      attributes.addFlashAttribute("message","用户名或密码错误");
      return "redirect:login";
    }
  }
  @GetMapping("/login")
  public String loginpage(Model model){
    model.addAttribute("user",new User());
    return "login";
  }

  @PostMapping("/register")
  public String register(@Valid User user,RedirectAttributes attributes, BindingResult bindingResult,Model model){
    model.addAttribute("user",user);
    if (bindingResult.hasErrors()){
      return "redirect:/user/register";
    }
    User existuser= userService.nameIsExits(user);
    if (existuser!=null){
      attributes.addFlashAttribute("message","用户名或昵称已存在");
      return "register";
    }
    if (user.getAvatar()==null || user.getAvatar().equals("")){
      user.setAvatar(avatar);
    }
    Boolean b=userService.save(user);
    if (b){
      attributes.addFlashAttribute("message","注册成功");
      return "redirect:/user/login";
    }else {
      attributes.addFlashAttribute("message","注册失败");
      return "redirect:/user/register";
    }

  }
  @GetMapping("/register")
  public String registerpage(Model model){
    model.addAttribute("user",new User());
    return "register";
  }

  @GetMapping("/layout")
  public String layout(HttpSession session){
    session.removeAttribute("user");
    return "redirect:/";
  }
}
