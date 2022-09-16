package com.tweetbox.logger.services;

import com.tweetbox.uploader.services.UploaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class LoggerService implements HandlerInterceptor {
  private final Logger logger = LoggerFactory.getLogger(LoggerService.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    request.setAttribute("start", new Date());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         @Nullable ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                              @Nullable Exception ex) throws Exception {
    Date startTime = (Date) request.getAttribute("start");
    Date endTime = new Date();
    Double diff = Double.valueOf(endTime.getTime() - startTime.getTime());
    Double diffSec = diff / 1000;
    String requestUrl = request.getRequestURI();
    String method = request.getMethod();
    String str = String.format("%s %s %f s", method.toUpperCase(), requestUrl, diffSec);
    logger.info(str);
  }

  public void Debug(String msg) {
    this.logger.debug(msg);
  }

  public void Info(String msg) {
    this.logger.info(msg);
  }

  public void Info(String format, Object arg) {
    this.logger.info(format, arg);
  }

  public void Trace(String msg) {
    this.logger.trace(msg);
  }

  public void Warn(String msg) {
    this.logger.warn(msg);

  }

  public void Error(String msg) {
    this.logger.error(msg);
  }

}
