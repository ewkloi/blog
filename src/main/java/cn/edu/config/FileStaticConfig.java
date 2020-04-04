package cn.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStaticConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /**
     *  /uploadFile/jpg/2.jpg
     *  file: /uploadFile/jpg/2.jpg
     *
     */
    registry.addResourceHandler("/blog/**").addResourceLocations("file:/blog/");
  }
}
