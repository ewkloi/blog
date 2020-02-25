package cn.edu.controller.user;


import cn.edu.po.Comment;
import cn.edu.po.User;
import cn.edu.service.BlogService;
import cn.edu.service.CommentService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

  @Autowired
  private CommentService service;
  @Autowired
  private BlogService blogService;
//  游客头像
  @Value("${comment.avatar}")
  private String avatar;

  @GetMapping("/comments/{blogId}")
  public String comments(@PathVariable String blogId, Model model) {
    List<Comment> comments = service.findByBlogId(blogId);
    model.addAttribute("comments", comments);
    return "blog :: commentList";
  }

  @PostMapping("/comments")
  public String post(Comment comment, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      comment.setAvatar(user.getAvatar());
      String uid = blogService.getUidByBlogId(comment.getBlogId());
      if (uid.equals(user.getId())) {
        comment.setIsower(true);
      }
    } else {
      comment.setAvatar(avatar);
    }
    Boolean b = service.save(comment);
    return "redirect:/comments/" + comment.getBlogId();
  }
}
