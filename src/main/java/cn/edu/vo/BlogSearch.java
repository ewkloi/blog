package cn.edu.vo;

import org.springframework.stereotype.Component;

/**
 * 封装博客查询条件的类
 */
@Component
public class BlogSearch {
  private String title;//标题
  private Long typeId;//分类
  private Boolean recommend;//是否推荐

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public Boolean getRecommend() {
    return recommend;
  }

  public void setRecommend(Boolean recommend) {
    this.recommend = recommend;
  }

  public BlogSearch() {
  }
}
