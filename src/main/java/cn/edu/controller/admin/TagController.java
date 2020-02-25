package cn.edu.controller.admin;


import cn.edu.po.Tag;
import cn.edu.service.TagService;

import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

  @Autowired
  private TagService tagService;
  @Value("${pageRequest.pagesize}")
  private int pageSize;
  //标签列表
  @GetMapping("/tags")
  public String types(Model model,
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

    PageInfo<Tag> pageInfo = tagService.getPageInfo(pageNum,pageSize);
    model.addAttribute("page", pageInfo);
    return "admin/tags";
  }
  //新增页面
  @GetMapping("/tags/input")
  public String input(Model model) {
    model.addAttribute("tag", new Tag());
    return "admin/tag_input";
  }
  //修改页面
  @GetMapping("/tags/{id}/input")
  public String editInput(@PathVariable Long id, Model model) {
    model.addAttribute("tag", tagService.getTagById(id));
    return "admin/tag_input";
  }
  //添加标签
  @PostMapping("/tags")
  public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
    Tag t = tagService.getTagByName(tag.getName());
    if (t != null) {
      result.rejectValue("name", "nameErrot", "添加失败，该标签已存在");
      return "admin/tag_input";
    }
    if (result.hasErrors()) {
      return "admin/tag_input";
    }
    Boolean b = tagService.saveTag(tag);
    if (b == null) {
      attributes.addFlashAttribute("message", "添加失败");
    } else {
      attributes.addFlashAttribute("message", "添加成功");
    }
    return "redirect:/admin/tags";
  }
  //修改标签
  @PostMapping("/tags/{id}")
  public String editpost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
    Tag t = tagService.getTagByName(tag.getName());
    if (t != null) {
      result.rejectValue("name", "nameErrot", "修改失败，该标签已存在");
      return "admin/tag_input";
    }
    if (result.hasErrors()) {
      return "admin/tag_input";
    }
    Boolean b = tagService.updateTag(tag);
    if (b) {
      attributes.addFlashAttribute("message", "修改成功");
    } else {
      attributes.addFlashAttribute("message", "修改失败");
    }
    return "redirect:/admin/tags";
  }
  //删除标签
  @GetMapping("/tags/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes attributes) {
    try {
      Boolean b = tagService.deleteTag(id);
      if(b){
        attributes.addFlashAttribute("message","删除成功");
      }else {
        attributes.addFlashAttribute("message","删除失败");
      }
    }catch (Exception e){
      attributes.addFlashAttribute("message","删除失败");
    }
    return "redirect:/admin/tags";
  }
}
