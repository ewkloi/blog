package cn.edu.service;


import cn.edu.po.User;
import com.github.pagehelper.PageInfo;

public interface UserService {
   User checkUser(String username, String password);
   Boolean save(User user);
   Boolean delete(String id);
   User nameIsExits(User user);
   PageInfo<User> list(int pageNum,int pageSize);
}
