package cn.edu.controller.user;

import cn.edu.po.Tag;
import cn.edu.po.Type;
import cn.edu.service.BlogService;
import cn.edu.service.TagService;
import cn.edu.service.TypeService;
import cn.edu.vo.IndexBlog;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

  @Autowired
  private BlogService blogService;
  @Autowired
  private TypeService typeService;
  @Autowired
  private TagService tagService;

  @Value("${pageRequest.pagesize}")
  private int pageSize;
  @Value("${index.typeSize}")
  private int typeSize;
  @Value("${index.tagSize}")
  private int tagSize;
  @Value("${index.nrBlogSize}")
  private int nrBlogSize;
  @Value("${index.nBlogSize}")
  private int nBlogSize;

  //  首页
  @GetMapping("/")
  public String index(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      Model model) {
    List<Type> types = typeService.getTopListType(tagSize);
    List<Tag> tags = tagService.getTopListTag(tagSize);
    List<IndexBlog> rblogs = blogService.getNewestRecommendBlog(nrBlogSize);
    model.addAttribute("types", types);
    model.addAttribute("tags", tags);
    model.addAttribute("rblogs", rblogs);

    PageInfo<IndexBlog> pageInfo = blogService.listIndexBlog(pageNum,pageSize);
    model.addAttribute("page", pageInfo);
    return "index";
  }

  //  博客详情
  @GetMapping("/blog/{id}")
  public String blog(@PathVariable String id, Model model) {
    IndexBlog blog = blogService.getDetailBlog(id);
    if (blog==null){
      return "error/404";
    }
    blogService.updateViews(id);
    model.addAttribute("blog", blog);
    return "blog";
  }

  //博客分类
  @GetMapping("/types")
  public String types(){
    return "index::typeList";
  }

  @GetMapping("/types/{id}")
  public String getBlogByType(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      @PathVariable Long id, Model model) {
//    顶端分类列表
    List<Type> types = typeService.getTopListType(10);
    model.addAttribute("types", types);
//    博客总数
    int total = 0;
    for (Type type : types) {
      total += type.getSize();
    }
    model.addAttribute("total", total);

    //根据id判断是否从首页点进来
    if (id == -1) {
      //首页点进来，默认选择博客最多的分类
      id = types.get(0).getId();
    }
    Type type=typeService.getTypeById(id);
    model.addAttribute("typeId", type.getId());
    model.addAttribute("typeName",type.getName());

    PageInfo<IndexBlog> pageInfo = blogService.getBlogByTypeId(id, pageNum,pageSize);
    model.addAttribute("page", pageInfo);

    return "types";
  }

  //  博客标签
  @GetMapping("/tags")
  public String tags(){
    return "index::tagList";
  }

  @GetMapping("/tags/{id}")
  public String getBlogByTag(
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
      @PathVariable Long id, Model model) {


    List<Tag> tags = tagService.getTopListTag(-1);
    model.addAttribute("tags", tags);
    int total = 0;
    for (Tag tag : tags) {
      total += tag.getSize();
    }
    model.addAttribute("total", total);
    //根据id判断是否从首页点进来
    if (id == -1) {
      id = tags.get(0).getId();
    }
    Tag tag=tagService.getTagById(id);
    PageInfo<IndexBlog> pageInfo = blogService.getBlogByTagId(id, pageNum,pageSize);
    //把tagId放进model，以便前台页面选中该标签
    model.addAttribute("tagId", tag.getId());
    model.addAttribute("tagName",tag.getName());
    model.addAttribute("page", pageInfo);

    return "tags";
  }

  //搜索


  @PostMapping("/search")
  public String search(@RequestParam String search, Model model) {
    List<IndexBlog> list = blogService.searchIndexBlog(search);
    model.addAttribute("query",search);
    model.addAttribute("list", list);
    return "search";
  }

//  footer获取最新博客
  @GetMapping("/footer/newblogs")
  public String newestBlogList(Model model){
    List<IndexBlog> nblogs = blogService.getNewestBlog(nBlogSize);
    model.addAttribute("nblogs", nblogs);
    return "_fragments :: newestBlogList";
  }
  //  关于我
  @GetMapping("/about")
  public String about(Model model) {
    return "about";
  }

  //  归档页面
  @GetMapping("/archives")
  public String archives(Model model) {
    Map<String, List<IndexBlog>> map=blogService.archiveBlog();
    int total=blogService.getBlogTotal();
    model.addAttribute("archiveMap",map);
    model.addAttribute("total",total);
    return "archives";
  }
}
