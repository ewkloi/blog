package cn.edu.po;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class Comment {
  private Long id;
  private String nickName;
  private String email;
  private String content;
  private String avatar;
  private Date createTime;

  private String blogId;
  private String blogTitle;
  private Long pid;
  private Comment parentComment;
  private List<Comment> replyComments=new ArrayList<>();

  private Boolean isower=false;

  public Comment() {
  }

  @Override
  public String toString() {
    return "Comment{" +
        "id=" + id +
        ", nickName='" + nickName + '\'' +
        ", email='" + email + '\'' +
        ", content='" + content + '\'' +
        ", avatar='" + avatar + '\'' +
        ", createTime=" + createTime +
        ", blogId='" + blogId + '\'' +
        ", pid=" + pid +
        ", replyComments=" + replyComments +
        '}';
  }

  public String getBlogTitle() {
    return blogTitle;
  }

  public void setBlogTitle(String blogTitle) {
    this.blogTitle = blogTitle;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public Boolean getIsower() {
    return isower;
  }

  public void setIsower(Boolean isower) {
    this.isower = isower;
  }

  public Comment getParentComment() {
    return parentComment;
  }

  public void setParentComment(Comment parentComment) {
    this.parentComment = parentComment;
  }

  public List<Comment> getReplyComments() {
    return replyComments;
  }

  public void setReplyComments(List<Comment> replyComments) {
    this.replyComments = replyComments;
  }
}
