package cn.edu.service;

import cn.edu.po.Type;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface TypeService {
  Boolean saveType(Type type);
  Type getTypeById(Long id);
  Type getTypeByName(String name);
  List<Type> listType();
  List<Type> getTopListType(int count);
  Boolean updateType(Type type);
  Boolean deleteType(Long id);
  PageInfo<Type> getPageInfo(int pageNum, int pageSize);
}
