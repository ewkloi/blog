package cn.edu.mapper;

import cn.edu.po.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentMapper {
  int save(Comment comment);
//  删除博客时调用
  List<Long>findIdsByBlogId(String bid);

  //删除该id评论
  int deleteById(Long id);
  //删除该id的所有回复
  int deleteByPid(Long pid);
  int updatePid(Long pid);
  List<Comment> findByBlogId(String blogId);
  List<Comment> findByPid(Long pid);

  List<Comment> list();
}
