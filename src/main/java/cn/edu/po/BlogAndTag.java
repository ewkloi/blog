package cn.edu.po;

import org.springframework.stereotype.Component;

@Component
public class BlogAndTag {
  private String blogId;
  private Long tagId;

  public BlogAndTag() {
  }

  public BlogAndTag(String blogId, Long tagId) {
    this.blogId = blogId;
    this.tagId = tagId;
  }

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }
}
