package cn.edu.service;

import cn.edu.po.Blog;
import cn.edu.vo.BlogQuery;
import cn.edu.vo.BlogSearch;
import cn.edu.vo.DetailBlog;
import cn.edu.vo.IndexBlog;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

public interface BlogService {
  Blog getBlogById(String id);
  Blog getUnPublishedBlog(String userId);
  IndexBlog getDetailBlog(String id);
  List<IndexBlog> getNewestRecommendBlog(int count);
  List<IndexBlog> getNewestBlog(int count);
  int getBlogTotal();
  String getUidByBlogId(String blogId);
  Map<String,List<IndexBlog>> archiveBlog();
  PageInfo<IndexBlog> getBlogByTypeId(Long id,int pageNum, int pageSize);
  PageInfo<IndexBlog> getBlogByTagId(Long id,int pageNum, int pageSize);
  PageInfo<IndexBlog> listIndexBlog(int pageNum, int pageSize);
  List<IndexBlog> searchIndexBlog(String search);
  PageInfo<BlogQuery> getPageInfo(int pageNum, int pageSize);
  PageInfo<BlogQuery> getPageInfoBySearch(int pageNum, int pageSize, BlogSearch blogSearch);
  Boolean saveBlog(Blog blog);
  Boolean updateBlog(Blog blog);
  void updateViews(String id);
  Boolean deleteBlog(String id);

}
