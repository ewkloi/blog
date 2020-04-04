package cn.edu.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

  public static String save(String rootPath, MultipartFile file) {
    File rootdir = new File(rootPath);
    if (!rootdir.exists()) {
      rootdir.mkdirs();
    }
    try {
//        原文件名
      String filePath="";
        String filename = file.getOriginalFilename();
//        所有文件保存在/blog/images文件夹下以文件后缀名命名的文件中，没有则创建文件夹
        if (!(filename == null || "".equals(filename))) {
          String[] str = filename.split("\\.");
          String endname = str[str.length - 1];
          String dir = rootPath + "/" + endname;
          File filrpath = new File(dir);
          if (!filrpath.exists()) {
            filrpath.mkdir();
          }
//        获取新文件名
          Date date = new Date();
         filePath = rootPath + "/" + endname + "/" + date.getTime() + filename;

          byte[] bytes = file.getBytes();
          Path path = Paths.get(filePath);
          Files.write(path, bytes);

        }
      return filePath;
    } catch (Exception e) {
      return "";
    }
  }
  public static Boolean delete(String path, String str) {
    if (str == null || str.trim().equals("")) {
      return true;
    }
    String[] urls = str.split(",");
    if (urls.length <= 0) {
      return false;
    }
    try {
      for (String url : urls) {
        url = url.replace(path, "");
        if (url.trim().equals("")) {
          return true;
        }
        File file = new File(url);
        if (file.exists()) {
          file.delete();
        }
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
