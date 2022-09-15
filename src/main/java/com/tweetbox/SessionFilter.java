package com.tweetbox;

import lombok.SneakyThrows;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SessionFilter implements Filter {
  @SneakyThrows
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    String path = req.getServletPath();
    if (path.indexOf("oauth") > -1) {
      chain.doFilter(request, response);
    } else {
      HttpSession httpSession = req.getSession();
      Object oauth = httpSession.getAttribute("OAuth");
      if (oauth == null) {
        throw new BadHttpRequest();
      }

      chain.doFilter(request, response);
    }
  }
}