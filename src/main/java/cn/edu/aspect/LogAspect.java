package cn.edu.aspect;

import cn.edu.vo.RequestLog;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

//  配置切面管理范围
  @Pointcut("execution(* cn.edu.controller..*.*(..))")
  public void log() {
  }
  @Before("log()")
  public void doBefore(JoinPoint joinPoint) {
    ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request=attributes.getRequest();
    String url=request.getRequestURI();
    String ip=request.getRemoteAddr();
    String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
    Object[] args=joinPoint.getArgs();
    RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
    logger.info("Request : {}",requestLog);
  }

  @AfterReturning(returning = "result", pointcut = "log()")
  public void doAfterReturn(Object result) {
    logger.info("Result : {}", result);
  }

}
