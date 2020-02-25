package cn.edu.po;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class Type {

  private Long id;
  @NotBlank(message = "分类名称不能为空")
  private String name;
  private int size;
  private List<Blog> blogs=new ArrayList<>();

  @Override
  public String toString() {
    return "Type{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public List<Blog> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<Blog> blogs) {
    this.blogs = blogs;
  }

  public Type() {
  }
}
