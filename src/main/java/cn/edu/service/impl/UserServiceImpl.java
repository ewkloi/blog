package cn.edu.service.impl;



import cn.edu.mapper.BlogMapper;
import cn.edu.mapper.CommentMapper;
import cn.edu.mapper.UserMapper;
import cn.edu.po.Blog;
import cn.edu.po.User;
import cn.edu.service.UserService;
import cn.edu.utils.FileUtils;
import cn.edu.utils.Md5Utils;
import cn.edu.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private BlogMapper blogMapper;
  @Autowired
  private CommentMapper commentMapper;
  @Override
  public User checkUser(String username, String password) {
    User Luser=new User();
    Luser.setUsername(username);
    Luser.setPassword(Md5Utils.md5(password));
    User user=userMapper.findByUsernameAndPassword(Luser);
    return user;
  }

  @Override
  public Boolean save(User user) {
    user.setId(StringUtils.UUID());
    user.setType(0);
    user.setPassword(Md5Utils.md5(user.getPassword()));
    Date date=new Date();
    user.setCreateTime(date);
    user.setUpdateTime(date);
    int i=userMapper.save(user);
    return i>0?true:false;
  }

  @Override
  public Boolean delete(String path,String id) {
//    先删除评论，再删除用户
    userMapper.delete(id);
    List<Blog> blogs=blogMapper.findByUid(id);
    int i=userMapper.delete(id);
    if (i>0){
      for (Blog blog:blogs){
        Pattern p = Pattern.compile("!\\[\\]\\((.*)\\)");
        Matcher m = p.matcher(blog.getContent());
        while (m.find()) {
          FileUtils.delete(path, m.group(1));
        }
      }
    }
    return i>0?true:false;
  }

  @Override
  public User nameIsExits(User user) {
    return userMapper.findByName(user);
  }

  @Override
  public PageInfo<User> list(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<User> list = userMapper.list();
    PageInfo<User> pageInfo = new PageInfo(list);
    return pageInfo;
  }

}
