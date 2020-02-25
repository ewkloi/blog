package cn.edu.service;

import cn.edu.po.Comment;
import com.github.pagehelper.PageInfo;
import java.util.List;


public interface CommentService {
  //保存评论
  Boolean save(Comment comment);
  //删除该评论同时删除评论下所有回复
  Boolean delete(Long id);
  //获取博客下所有评论
  List<Comment> findByBlogId(String id);

  PageInfo<Comment> list(int pageNum,int pageSize);
}
