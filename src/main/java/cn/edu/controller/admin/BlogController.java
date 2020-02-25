package cn.edu.controller.admin;

import cn.edu.po.Blog;
import cn.edu.po.Tag;
import cn.edu.po.User;
import cn.edu.service.BlogService;
import cn.edu.service.TagService;
import cn.edu.service.TypeService;
import cn.edu.vo.BlogSearch;
import cn.edu.vo.IndexBlog;
import cn.edu.vo.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogController {

  @Autowired
  private BlogService blogService;
  @Autowired
  private TypeService typeService;
  @Autowired
  private TagService tagService;
  @Value("${pageRequest.pagesize}")
  private int pageSize;
  public void setTypeAndTagToModel(Model model) {
    model.addAttribute("types", typeService.listType());
    model.addAttribute("tags", tagService.listTag());
  }

  private void setTypeToModel(Model model) {
    model.addAttribute("types", typeService.listType());
  }

  private List<Tag> setTagsToBlog(Blog blog) {
    if (blog.getTagIds() == null || blog.getTagIds().equals("")) {
      return null;
    }
    String[] tagIds = blog.getTagIds().split(",");
    if (tagIds == null) {
      return null;
    }
    List<Tag> listTag = new ArrayList<>();
    for (String tagid : tagIds) {
      listTag.add(tagService.getTagById(Long.parseLong(tagid)));
    }
    return listTag;

  }

  //博客首页
  @GetMapping("/blogs")
  public String listblog(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      Model model) {

    model.addAttribute("page", blogService.getPageInfo(pageNum,pageSize));
    setTypeToModel(model);
    return "admin/blogs";
  }

  //发布、保存博客
  @PostMapping("/blogs")
  public String post(@Valid Blog blog, BindingResult bindingResult, RedirectAttributes attributes, HttpSession session) {
    if (bindingResult.hasErrors()){

    }
    blog.setType(typeService.getTypeById(blog.getTypeId()));
    blog.setUser((User) session.getAttribute("user"));
    blog.setTags(setTagsToBlog(blog));
    Boolean b;
    if (blog.getId() == null || blog.getId() == "") {
      b = blogService.saveBlog(blog);
    }else {
      b=blogService.updateBlog(blog);
    }
    Boolean published = blog.isPublished();
    String message = null;
    if (published) {//发布
      if (b) {
        message = "博客发布成功";
      } else {
        message = "博客发布失败";
      }
    } else {//保存
      if (b) {
        message = "博客已保存到草稿";
      } else {
        message = "博客保存失败";
      }
    }
    attributes.addFlashAttribute("message", message);
    return "redirect:/admin/blogs";
  }

  //跳转到发布博客页面
  @GetMapping("/blogs/input")
  public String blogInputPage(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    Blog blog = blogService.getUnPublishedBlog(user.getId());
    model.addAttribute("blog", blog == null ? new Blog() : blog);
    setTypeAndTagToModel(model);
    return "admin/blogs_input";
  }

  //跳转到修改博客页面
  @GetMapping("/blogs/{id}/input")
  public String blogUpdatePage(@PathVariable String id, Model model) {
    Blog blog = blogService.getBlogById(id);
    model.addAttribute("blog", blog);
    setTypeAndTagToModel(model);
    return "admin/blogs_update";
  }
  @GetMapping("/blogs/{id}/check")
  public String blogCheckPage(@PathVariable String id, Model model) {
    IndexBlog blog = blogService.getDetailBlog(id);
    model.addAttribute("blog", blog);
    setTypeAndTagToModel(model);
    return "admin/blog";
  }

  //修改博客
  @PostMapping("/blogs/{id}")
  public String updateBlog(@Valid Blog blog, RedirectAttributes attributes) {
    blog.setType(typeService.getTypeById(blog.getTypeId()));
    blog.setTags(setTagsToBlog(blog));
    Boolean b = blogService.updateBlog(blog);
    String message = null;
    if (b) {
      message = "博客修改成功";
    } else {
      message = "博客修改失败";
    }
    attributes.addFlashAttribute("message", message);
    return "redirect:/admin/blogs";
  }

  //查询博客
  @PostMapping("/blogs/search")
  public String search(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      BlogSearch blogSearch, Model model) {
    model.addAttribute("page", blogService.getPageInfoBySearch(pageNum,pageSize, blogSearch));
    setTypeToModel(model);
    return "admin/blogs";
  }

  //删除博客
  @GetMapping("/blogs/{id}/delete")
  public String delete(@PathVariable String id, RedirectAttributes attributes) {
    Boolean b = blogService.deleteBlog(id);
    if (b) {
      attributes.addFlashAttribute("message", "删除成功");
    } else {
      attributes.addFlashAttribute("message", "删除失败");
    }
    return "redirect:/admin/blogs";
  }
}
