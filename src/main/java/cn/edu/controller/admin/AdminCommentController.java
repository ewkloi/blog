package cn.edu.controller.admin;

import cn.edu.po.Comment;
import cn.edu.po.User;
import cn.edu.service.BlogService;
import cn.edu.service.CommentService;
import com.github.pagehelper.PageInfo;
import java.util.List;
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
public class AdminCommentController {
  @Autowired
  private CommentService commentService;

  @Autowired
  private BlogService blogService;

  @Value("${pageRequest.pagesize}")
  private int pageSize;

  @GetMapping("/comments")
  public String comments(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      Model model){
    PageInfo<Comment> pageInfo=commentService.list(pageNum,pageSize);
    model.addAttribute("page",pageInfo);
    return "admin/comments";
  }
  @PostMapping("/comments")
  public String post(Comment comment, HttpSession session) {
    User user = (User) session.getAttribute("user");
    comment.setAvatar(user.getAvatar());
    String uid = blogService.getUidByBlogId(comment.getBlogId());
    if (uid.equals(user.getId())) {
      comment.setIsower(true);
    }
    Boolean b = commentService.save(comment);
    return "redirect:/admin/comments/" + comment.getBlogId();
  }
  @GetMapping("/comments/{blogId}")
  public String comments(@PathVariable String blogId, Model model) {
    List<Comment> comments = commentService.findByBlogId(blogId);
    model.addAttribute("comments", comments);
    return "admin/blog :: commentList";
  }

  @GetMapping("/comments/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes attributes){
    Boolean b=commentService.delete(id);
    if (b){
      attributes.addFlashAttribute("message","删除成功");
    }else {
      attributes.addFlashAttribute("message","删除失败");
    }
    return "redirect:/admin/comments";
  }
}
