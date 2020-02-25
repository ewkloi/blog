package cn.edu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
  /**
   * MD5加密类
   * @param str 要加密的字符串
   * @return  加密后的字符串
   */
  public static String md5(String str) {
    try {
      // 得到一个信息摘要器
      MessageDigest md = MessageDigest.getInstance("md5");
      byte[] result = md.digest(str.getBytes());
      StringBuffer buffer = new StringBuffer();
      // 把每一个byte 做一个与运算 0xff;
      for (byte b : result) {
        // 与运算
        int number = b & 0xff;// 加盐
        String s = Integer.toHexString(number);
        if (s.length() == 1) {
          buffer.append("0");
        }
        buffer.append(s);
      }
      // 标准的md5加密后的结果
      return buffer.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    }
  }

  public static void main(String[] args) {
    System.out.println(md5("123456"));
    //e10adc3949ba59abbe56e057f20f883e
  }
}
