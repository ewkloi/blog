package cn.edu.controller.admin;

import cn.edu.po.Type;
import cn.edu.service.TypeService;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.web.PageableDefault;
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
public class TypeController {

  @Autowired
  private TypeService typeService;

  @Value("${pageRequest.pagesize}")
  private int pageSize;

  @GetMapping("/types")
  public String types(Model model,
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

    PageInfo<Type> pageInfo = typeService.getPageInfo(pageNum,pageSize);
    model.addAttribute("page", pageInfo);
    return "admin/types";
  }

  @GetMapping("/types/input")
  public String input(Model model) {
    model.addAttribute("type", new Type());
    return "admin/type_input";
  }

  @GetMapping("/types/{id}/input")
  public String editInput(@PathVariable Long id, Model model) {
    model.addAttribute("type", typeService.getTypeById(id));
    return "admin/type_input";
  }

  @PostMapping("/types")
  public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
    Type t = typeService.getTypeByName(type.getName());
    if (t != null) {
      result.rejectValue("name", "nameError", "添加失败，该分类已存在");
      return "admin/type_input";
    }
    if (result.hasErrors()) {
      return "admin/type_input";
    }
    Boolean b = typeService.saveType(type);
    if (b == null) {
      attributes.addFlashAttribute("message", "添加失败");
    } else {
      attributes.addFlashAttribute("message", "添加成功");
    }
    return "redirect:/admin/types";
  }

  @PostMapping("/types/{id}")
  public String editpost(@Valid Type type, BindingResult result, @PathVariable Long id,
      RedirectAttributes attributes) {
    Type t = typeService.getTypeByName(type.getName());
    if (t != null) {
      result.rejectValue("name", "nameErrot", "修改失败，该分类已存在");
      return "admin/type_input";
    }
    if (result.hasErrors()) {
      return "admin/type_input";
    }
    Boolean b = typeService.updateType(type);
    if (b == null) {
      attributes.addFlashAttribute("message", "修改失败");
    } else {
      attributes.addFlashAttribute("message", "修改成功");
    }
    return "redirect:/admin/types";
  }

  @GetMapping("/types/{id}/delete")
  public String delete(@PathVariable Long id,RedirectAttributes attributes) {
    try {
      Boolean b=typeService.deleteType(id);
      if(b){
        attributes.addFlashAttribute("message","删除成功");
      }else {
        attributes.addFlashAttribute("message","删除失败");
      }
    }catch (Exception e){
      attributes.addFlashAttribute("message","删除失败");
    }
    return "redirect:/admin/types";
  }
}
