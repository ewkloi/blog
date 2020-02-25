package cn.edu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class VcblogApplication {
  @Bean
  public RequestContextListener requestContextListener(){
    return new RequestContextListener();
  }

  public static void main(String[] args) {
    SpringApplication.run(VcblogApplication.class, args);
  }
}
