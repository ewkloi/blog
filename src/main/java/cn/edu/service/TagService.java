package cn.edu.service;

import cn.edu.po.Tag;
import cn.edu.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface TagService {
  Boolean saveTag(Tag tag);
  Boolean saveList(List<Tag> list);
  Tag getTagById(Long id);

  Tag getTagByName(String name);
  List<Tag> listTag();
  List<Tag> getTopListTag(int count);
  List<Tag> listTag(String blogId);
  Boolean updateTag(Tag tag);
  Boolean deleteTag(Long id);
  PageInfo<Tag> getPageInfo(int pageNum, int pageSize);
}
