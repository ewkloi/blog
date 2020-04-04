package cn.edu.utils;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class StringUtils {

  public static String getItemPath(HttpServletRequest request){
    String scheme = request.getScheme(); // 获取链接协议，http
    String serverName = request.getServerName(); // 获取服务器名称 localhost
    int serverPort = request.getServerPort(); // 获取端口 8080
    String path = scheme+"://"+serverName+":"+serverPort;
    return path;
  }
  public static String UUID(){
    return UUID.randomUUID().toString();
  }
}
