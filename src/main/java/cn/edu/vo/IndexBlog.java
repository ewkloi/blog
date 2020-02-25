package cn.edu.vo;

import cn.edu.po.Tag;
import cn.edu.po.Type;
import cn.edu.po.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class IndexBlog {
  private String id;
  private String title;//标题
  private String description;//博客描述
  private String content;//内容
  private String firstPicture;//首图
  private String flag;//标记{原创，转载，声明}
  private Integer views;//浏览次数
  private boolean appreciation;//赞赏开启
  private boolean shareStatement;//转载声明
  private boolean commentabled;//评论开启
  private Date updateTime;//更新时间

  private User user;
  private Type type;
  private List<Tag> tags = new ArrayList<>();//标签

  public IndexBlog() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFirstPicture() {
    return firstPicture;
  }

  public void setFirstPicture(String firstPicture) {
    this.firstPicture = firstPicture;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public boolean isAppreciation() {
    return appreciation;
  }

  public void setAppreciation(boolean appreciation) {
    this.appreciation = appreciation;
  }

  public boolean isShareStatement() {
    return shareStatement;
  }

  public void setShareStatement(boolean shareStatement) {
    this.shareStatement = shareStatement;
  }

  public boolean isCommentabled() {
    return commentabled;
  }

  public void setCommentabled(boolean commentabled) {
    this.commentabled = commentabled;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
}
