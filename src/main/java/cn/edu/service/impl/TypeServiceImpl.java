package cn.edu.service.impl;

import cn.edu.exception.NotFoundException;
import cn.edu.mapper.TypeMapper;
import cn.edu.po.Type;
import cn.edu.service.TypeService;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

  @Autowired
  private TypeMapper typeMapper;

  @Override
  public Boolean saveType(Type type) {
    int k = typeMapper.save(type.getName());

    return k > 0 ? true : false;
  }

  //
  public Boolean saveList(List<Type> list) {
    int n = 0;
    for (Type type : list) {
      try {
        typeMapper.save(type.getName());
        n++;
      } catch (Exception e) {
        break;
      }
    }
    return n == list.size() ? true : false;
  }

  @Override
  public Type getTypeById(Long id) {
    return typeMapper.findById(id);
  }

  @Override
  public Type getTypeByName(String name) {
    return typeMapper.findByName(name);
  }

  @Override
  public List<Type> listType() {
    return typeMapper.list();
  }

  @Override
  public List<Type> getTopListType(int count) {
    return typeMapper.getTopType(count);
  }

  @Override
  public Boolean updateType(Type type) {
    Type t = typeMapper.findById(type.getId());
    if (t==null) {
      throw new NotFoundException("不存在该类型");
    }
    int k = typeMapper.update(type);
    return k > 0 ? true : false;
  }

  @Override
  public Boolean deleteType(Long id) {
    int i = typeMapper.delete(id);
    return i > 0 ? true : false;
  }

  @Override
  public PageInfo<Type> getPageInfo(int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<Type> list = typeMapper.list();
    PageInfo<Type> pageInfo = new PageInfo(list);
    return pageInfo;
  }
}
