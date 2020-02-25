package cn.edu.mapper;

import cn.edu.po.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

  List<User> list();

  User findByUsernameAndPassword(User user);

  User findById(String id);

  User findByName(User user);
  int save(User user);

  void delete(String id);

//  @Select("select * from t_user where username=#{username} and password=#{password}")
//  User findByUsernameAndPassword(User user);
//  @Select("select * from t_user where id=#{id}")
//  User findById(@Param("id") Long id);
}
