package cn.edu.mapper;

import cn.edu.po.BlogAndTag;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogAndTagMapper {
  int save(BlogAndTag blogAndTag);
  int delete(BlogAndTag blogAndTag);
  int deleteByBlogId(String id);
  List<BlogAndTag> findByBlogId(String blogId);
  List<BlogAndTag> findByTagId(Long tagId);
}
