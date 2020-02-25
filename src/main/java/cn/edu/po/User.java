package cn.edu.po;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class User {
  private String id;
  @NotEmpty(message = "昵称不能为空")
  private String nickName;
  @NotEmpty(message = "用户名不能为空")
  private String username;
  @NotEmpty(message = "密码不能为空")
  private String password;
  @NotEmpty(message = "邮箱不能为空")
  private String email;
  private String avatar;//头像
  private Integer type=0;
  private Date createTime;
  private Date updateTime;
  private List<Blog> blogs=new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public List<Blog> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<Blog> blogs) {
    this.blogs = blogs;
  }

  public User() {
  }
}
