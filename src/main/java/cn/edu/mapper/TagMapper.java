package cn.edu.mapper;


import cn.edu.po.Tag;
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
public interface TagMapper {

  int save(String name);

  Tag findById(Long id);

  List<Tag> findByBlogId(String id);

  /**
   *获取使用次数最多的n个标签
   * @param num 标签数量
   * @return
   */
  List<Tag> getTopTag(int num);


  Tag findByName( String name);

  List<Tag> list();

  int update(Tag tag);

  void delete( Long id);

}
