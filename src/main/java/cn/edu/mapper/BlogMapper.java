package cn.edu.mapper;

import cn.edu.po.Blog;
import cn.edu.vo.BlogQuery;
import cn.edu.vo.BlogSearch;
import cn.edu.vo.DetailBlog;
import cn.edu.vo.IndexBlog;
import cn.edu.vo.PageRequest;
import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogMapper {
  int updateViews(String id);
  Blog findById(String id);
  IndexBlog getDetailBlog(String id);
  int getBlogTotal();
  String getUidByBlogId(String id);
  List<String> getGroupYear();
  List<IndexBlog> getBlogByYear(String year);
  List<IndexBlog> getListIndexBlog();
  List<IndexBlog> getSearchIndexBlog(String search);
  List<IndexBlog> getBlogByType(Long id);
  List<IndexBlog> getBlogByTag(Long id);
  List<IndexBlog> getNewestRecommendBlog(int num);
  List<IndexBlog> getNewestBlog(int num);
  List<BlogQuery> listQuery();
  List<BlogQuery> getBlogBySearch(BlogSearch blogSearch);

  /**
   *
   * @param id 用户id
   * @return
   */
  Blog getUnPublishedBlog(String id);

  int save(Blog blog);

  int update(Blog blog);

  int delete(String id);

}
