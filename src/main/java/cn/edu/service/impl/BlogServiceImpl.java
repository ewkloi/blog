package cn.edu.service.impl;

import cn.edu.exception.NotFoundException;
import cn.edu.mapper.BlogAndTagMapper;
import cn.edu.mapper.BlogMapper;
import cn.edu.mapper.CommentMapper;
import cn.edu.mapper.TagMapper;
import cn.edu.mapper.TypeMapper;
import cn.edu.po.Blog;
import cn.edu.po.BlogAndTag;
import cn.edu.po.Tag;
import cn.edu.service.BlogService;
import cn.edu.service.CommentService;
import cn.edu.utils.MarkDownUtils;
import cn.edu.utils.StringUtils;
import cn.edu.vo.BlogQuery;
import cn.edu.vo.BlogSearch;
import cn.edu.vo.IndexBlog;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

  @Autowired
  private BlogMapper blogMapper;
  @Autowired
  private BlogAndTagMapper blogAndTagMapper;
  @Autowired
  private TypeMapper typeMapper;
  @Autowired
  private TagMapper tagMapper;
  @Autowired
  private CommentMapper commentMapper;

  private void setTypeAndTagsToBlog(Blog blog){
    blog.setType(typeMapper.findById(blog.getTypeId()));
    blog.setTags(tagMapper.findByBlogId(blog.getId()));

  }

  private void saveTags(Blog blog){
    List<Tag> list = blog.getTags();
    if (list==null)
      return;
    BlogAndTag bt = new BlogAndTag();
    bt.setBlogId(blog.getId());
    for (Tag tag : list) {
      bt.setTagId(tag.getId());
      blogAndTagMapper.save(bt);
    }
  }
  @Override
  public Blog getBlogById(String id) {
    Blog blog=blogMapper.findById(id);
    setTypeAndTagsToBlog(blog);
    return blog;
  }
  @Override
  public Blog getUnPublishedBlog(String userId) {
    Blog blog=blogMapper.getUnPublishedBlog(userId);
    if(blog!=null){
      setTypeAndTagsToBlog(blog);
    }

    return blog;
  }
  @Override
  public IndexBlog getDetailBlog(String id) {
    IndexBlog blog=blogMapper.getDetailBlog(id);
    if (blog==null){
      throw new NotFoundException("该博客不存在");
    }
    String content=blog.getContent();
    blog.setContent(MarkDownUtils.getHtmlContent(content));
    return blog;
  }
  @Override
  public List<IndexBlog> getNewestRecommendBlog(int count) {
    return blogMapper.getNewestRecommendBlog(count);
  }
  @Override
  public List<IndexBlog> getNewestBlog(int count) {
    return blogMapper.getNewestBlog(count);
  }

  @Override
  public int getBlogTotal() {
    return blogMapper.getBlogTotal();
  }

  @Override
  public String getUidByBlogId(String blogId) {
    return blogMapper.getUidByBlogId(blogId);
  }

  //  博客归档
  @Override
  public Map<String, List<IndexBlog>> archiveBlog() {
    List<String> years=blogMapper.getGroupYear();
    Map<String,List<IndexBlog>> map=new LinkedHashMap<>();
    for (String year:years){
        map.put(year,blogMapper.getBlogByYear(year));
    }
    return map;
  }

  private  void setTagsToBlog(List<IndexBlog> list){
    for (int i=0;i<list.size();i++){
      IndexBlog blog=list.get(i);
      List<Tag> tags=tagMapper.findByBlogId(blog.getId());
      blog.setTags(tags);
      list.set(i,blog);
    }
  }

  private void moveTagToFirst(Long id,List<IndexBlog> list){
    for (int i=0;i<list.size();i++) {
      List<Tag> tags=list.get(i).getTags();
      for (int j = 1; tags.size() > 1 && j < tags.size(); j++) {
        if (tags.get(j).getId().equals(id)) {
          Tag tag = tags.get(0);
          tags.set(0, tags.get(j));
          tags.set(j, tag);
          break;
        }
      }
    }
  }

  /**
   * 根据分类获取博客时，要注意有些博客是没有标签的
   */
  @Override
  public PageInfo<IndexBlog> getBlogByTypeId(Long id, int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<IndexBlog> list = blogMapper.getBlogByType(id);
    PageInfo<IndexBlog> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }

  /**
   * 根据标签Id获取博客时，只能获取单一标签，无法获取博客的其他标签，
   * 所以要找出博客的所有标签
   */
  @Override
  public PageInfo<IndexBlog> getBlogByTagId(Long id, int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<IndexBlog> list = blogMapper.getBlogByTag(id);
    setTagsToBlog(list);
    moveTagToFirst(id,list);
    PageInfo<IndexBlog> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }
//  获取首页博客列表
  @Override
  public PageInfo<IndexBlog> listIndexBlog(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    List<IndexBlog> list = blogMapper.getListIndexBlog();
    PageInfo<IndexBlog> pageInfo = new PageInfo<>(list);
    pageInfo.setTotal(blogMapper.getBlogTotal());
    return pageInfo;
  }
//  获取前台搜索博客
  @Override
  public List<IndexBlog> searchIndexBlog(String search) {
    List<IndexBlog> list = blogMapper.getSearchIndexBlog(search);
    return list;
  }
//  后台获取简单博客列表
  @Override
  public PageInfo<BlogQuery> getPageInfo(int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<BlogQuery> list = blogMapper.listQuery();
    PageInfo<BlogQuery> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }
//  后台获取搜索博客
  @Override
  public PageInfo<BlogQuery> getPageInfoBySearch(int pageNum, int pageSize, BlogSearch search) {
    PageHelper.startPage(pageNum, pageSize);
    List<BlogQuery> list = blogMapper.getBlogBySearch(search);
    PageInfo<BlogQuery> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }
//  保存博客
  @Override
  public Boolean saveBlog(Blog blog) {
    blog.setId(StringUtils.UUID());
    blog.setUserId(blog.getUser().getId());
    Date date = new Date();
    blog.setCreateTime(date);
    blog.setUpdateTime(date);
//    保存博客
    blogMapper.save(blog);
//    保存标签
    saveTags(blog);
    return true;
  }
  /**
   * 修改博客步骤：
   * 1.判断博客是否存在
   * 2.删除该博客的所有标签
   * 3.修改博客
   * 4.把博客标签添加到表中
   * @param blog
   * @return
   */
  @Override
  public Boolean updateBlog(Blog blog) {

    Blog b = blogMapper.findById(blog.getId());
    if (b == null) {
      throw new NotFoundException("该博客不存在");
    }
    blog.setUpdateTime(new Date());
    blogAndTagMapper.deleteByBlogId(blog.getId());
    blogMapper.update(blog);
    saveTags(blog);
    return true;
  }
  @Override
  public void updateViews(String id) {
    blogMapper.updateViews(id);
  }
  @Override
  public Boolean deleteBlog(String id) {
    List<Long> cids=commentMapper.findIdsByBlogId(id);
    for(Long cid:cids){
      commentMapper.deleteById(cid);
    }
    blogAndTagMapper.deleteByBlogId(id);
    blogMapper.delete(id);
    return true;
  }
}
