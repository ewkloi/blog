package cn.edu.service.impl;

import cn.edu.mapper.CommentMapper;
import cn.edu.po.Comment;
import cn.edu.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentMapper mapper;

  @Override
  @Transactional
  public Boolean save(Comment comment) {
    comment.setCreateTime(new Date());
//    标记是否为顶级评论
    Boolean isTop = false;
    if (comment.getPid() == -1) {
      comment.setPid(null);
      isTop = true;
    }
//    mapper的save方法会把自增主键放到comment的id中
    int i = mapper.save(comment);
    if (isTop) {
      mapper.updatePid(comment.getId());
    }
    return i > 0 ? true : false;
  }

  @Override
  public PageInfo<Comment> list(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Comment> list = mapper.list();
    PageInfo<Comment> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }
  /**
   * 删除评论及所有回复 1.找出评论下的递归回复列表 2.从最底层回复开始删除
   */
  @Transactional
  @Override
  public Boolean delete(Long id) {
    deleteAllReply(id);
    return true;
  }

  private void deleteAllReply(Long id) {

    List<Comment> replys = mapper.findByPid(id);

    if (replys == null || replys.size() == 0) {
//      如果该评论下没有回复，则删除该评论
      mapper.deleteById(id);
      return;
    }
    for (int i = 0; i < replys.size(); i++) {
      deleteAllReply(replys.get(i).getId());
    }

  }

  //  递归填充每条顶级评论下的所有回复以及回复下的回复
  private void setReplyToComment(Comment comment) {
    List<Comment> replys = mapper.findByPid(comment.getId());
    if (replys.size() > 0) {

      comment.setReplyComments(replys);
      for (int i = 0; i < replys.size(); i++) {
        //填充子回复的parentComment
        Comment reply=replys.get(i);

        Comment parentComment=new Comment();
        parentComment.setId(comment.getId());
        parentComment.setNickName(comment.getNickName());

        reply.setParentComment(parentComment);
        replys.set(i,reply);
        setReplyToComment(reply);
      }
    }
  }

  //  由于评论的层级可能很深
//  返回list时要把list里的所有comment修改成二级
  @Override
  public List<Comment> findByBlogId(String id) {
    //博客下所有顶级评论
    List<Comment> comments = mapper.findByBlogId(id);
    //填充每条顶级评论下的所有回复
    for (int i = 0; i < comments.size(); i++) {
      Comment comment = comments.get(i);
//      填充子评论的parentComment
      setReplyToComment(comment);
      comments.set(i, comment);
    }
    eachComments(comments);
    return comments;
  }


  private void eachComments(List<Comment> comments) {
//    遍历所有顶级评论
    for (int i = 0; i < comments.size(); i++) {
      Comment comment=comments.get(i);
      List<Comment> replys = comment.getReplyComments();
//      该顶级评论下所有二级评论
      for (int j = 0; j < replys.size(); j++) {
//        把二级评论下所有评论折叠为二级评论
        setChildrenReplys(replys.get(j));
      }
      comment.setReplyComments(temReplys);
      comments.set(i,comment);
      temReplys=new ArrayList<>();
    }
  }
  //  临时容器：存放所有回复
  private List<Comment> temReplys = new ArrayList<>();
//这里的comment是二级评论
  private void setChildrenReplys(Comment comment) {
//    先把当前评论加入临时list
    temReplys.add(comment);
    if(comment.getReplyComments().size()>0){
//      三级评论
      List<Comment> replys=comment.getReplyComments();
      for (int i=0;i<replys.size();i++){{
//        递归
        setChildrenReplys(replys.get(i));
      }
      comment.setReplyComments(null);
      }
    }
  }


}
