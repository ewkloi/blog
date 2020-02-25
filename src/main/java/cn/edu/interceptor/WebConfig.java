package cn.edu.interceptor;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 把拦截器添加到spring容器中
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin", "/admin/login");
  }
  @Bean
  public RequestContextListener requestContextListenerBean() {
    return new RequestContextListener();
  }
}
