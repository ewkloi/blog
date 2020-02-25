package cn.edu.service.impl;

import cn.edu.exception.NotFoundException;
import cn.edu.mapper.TagMapper;
import cn.edu.po.Tag;
import cn.edu.service.TagService;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

  @Autowired
  private TagMapper tagMapper;

  @Override
  public Boolean saveTag(Tag tag) {
    int k = tagMapper.save(tag.getName());
    return k > 0 ? true : false;
  }

  @Override
  public Boolean saveList(List<Tag> list) {
    int n = 0;
    for (Tag tag : list) {
      try {
        tagMapper.save(tag.getName());
        n++;
      } catch (Exception e) {
        break;
      }
    }
    return n == list.size() ? true : false;
  }

  @Override
  public Tag getTagById(Long id) {
    return tagMapper.findById(id);
  }

  @Override
  public Tag getTagByName(String name) {
    return tagMapper.findByName(name);
  }

  @Override
  public List<Tag> listTag() {
    return tagMapper.list();
  }

  @Override
  public List<Tag> getTopListTag(int count) {
    List<Tag> list = tagMapper.getTopTag(count);
    sort(list);
    return list;
  }

  private void sort(List<Tag> list) {
    for (int i = 0; i < list.size() - 1; i++) {
      Tag t1 = list.get(i);
      for (int j = i + 1; j < list.size() - i - 1; j++) {
        Tag t2 = list.get(j);
        if (t1.getSize() < t2.getSize()) {
          Tag tag = list.get(i);
          list.set(i, t2);
          list.set(j, tag);
        }
      }
    }
  }

  @Override
  public List<Tag> listTag(String blogId) {
    return tagMapper.findByBlogId(blogId);
  }

  @Override
  public Boolean updateTag(Tag tag) {
    Tag t = tagMapper.findById(tag.getId());
    if (t == null) {
      throw new NotFoundException("不存在该标签");
    }
    int k = tagMapper.update(tag);
    return k > 0 ? true : false;
  }

  @Override
  public Boolean deleteTag(Long id) {
    try {
      tagMapper.delete(id);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public PageInfo<Tag> getPageInfo(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Tag> list = tagMapper.list();
    PageInfo<Tag> pageInfo = new PageInfo(list);
    return pageInfo;
  }
}
