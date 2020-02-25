package cn.edu.mapper;

import cn.edu.po.Type;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TypeMapper {

  int save(String name);

  Type findById(Long id);

  Type findByName(String name);

  /**
   * 获取博客最多的前n个分类
   * @param num
   * @return
   */
  List<Type> getTopType(int num);

  List<Type> list();

  int update(Type type);

  int delete(Long id);

}
