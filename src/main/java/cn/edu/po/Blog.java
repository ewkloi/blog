package cn.edu.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/*
po包对应的是数据库下的类
 */
@Component
public class Blog {

  private String id;
  @NotNull(message = "标题不能为空")
  private String title;//标题
  @NotNull(message = "博客描述不能为空")
  private String description;//博客描述
  @NotNull(message = "内容不能为空")
  private String content;//内容
  @NotNull(message = "首图不能为空")
  private String firstPicture;//首图
  private String flag;//标记{原创，转载，声明}
  private Integer views=0;//浏览次数
  private boolean appreciation;//赞赏开启
  private boolean shareStatement;//转载声明
  private boolean commentabled;//评论开启
  private boolean published;//发布
  private boolean recommend;//推荐
  private Date createTime;//创建时间
  private Date updateTime;//更新时间

  private String userId;
  @NotNull(message = "所属分类不能为空")
  private Long typeId;

  private String tagIds;

  private Type type;//博客所属分类
  private List<Tag> tags = new ArrayList<>();//标签
  private User user;//撰写博客的用户
  private List<Comment> comments = new ArrayList<>();//评论

  @Override
  public String toString() {
    return "Blog{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", content='" + content + '\'' +
        ", firstPicture='" + firstPicture + '\'' +
        ", flag='" + flag + '\'' +
        ", views=" + views +
        ", appreciation=" + appreciation +
        ", shareStatement=" + shareStatement +
        ", commentabled=" + commentabled +
        ", published=" + published +
        ", recommend=" + recommend +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", userId='" + userId + '\'' +
        ", typeId=" + typeId +
        ", tagIds='" + tagIds + '\'' +
        ", type=" + type +
        ", tags=" + tags +
        ", user=" + user +
        ", comments=" + comments +
        '}';
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

  public String getFirstPicture() {
    return firstPicture;
  }

  public void setFirstPicture(String firstPicture) {
    this.firstPicture = firstPicture;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
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

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public boolean isRecommend() {
    return recommend;
  }

  public void setRecommend(boolean recommend) {
    this.recommend = recommend;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public String getTagIds() {
    return tagIds;
  }

  public void setTagIds(String tagIds) {
    this.tagIds = tagIds;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public Blog() {
  }
}
