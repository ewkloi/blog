package cn.edu.vo;

import cn.edu.po.Type;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * 封装博客查询结果的类
 */
@Component
public class BlogQuery {
  private String id;
  private String title;
  private Date updateTime;
  private Boolean published;
  private Boolean recommend;
  private Long typeId;
  private Type type;

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

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Boolean getPublished() {
    return published;
  }

  public void setPublished(Boolean published) {
    this.published = published;
  }

  public Boolean getRecommend() {
    return recommend;
  }

  public void setRecommend(Boolean recommend) {
    this.recommend = recommend;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public BlogQuery() {
  }
}
