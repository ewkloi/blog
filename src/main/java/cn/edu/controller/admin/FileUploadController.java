package cn.edu.controller.admin;

import cn.edu.utils.FileUtils;
import cn.edu.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/file")
public class FileUploadController {

  @Value("${default.dir}")
  private String dir;

  @ResponseBody
  @PostMapping("/upload")
  public JSONObject blogFile(@RequestParam("editormd-image-file") MultipartFile file,
      HttpServletRequest request) {
    String picDir = FileUtils.save(dir,file);
    String path= StringUtils.getItemPath(request);
    String url = path+picDir;
    JSONObject json = new JSONObject();
    // 图片上传后地址
    json.put("url", url); ///图片地址和上传后的文件名
    // 图片上传的状态 1成功0失败
    json.put("success", 1);
    // 图片上传回传的信息
    json.put("message", "upload success!");
    return json;
  }

}
