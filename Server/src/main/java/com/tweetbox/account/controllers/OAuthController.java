package com.tweetbox.account.controllers;

import com.tweetbox.account.dtos.ReqSignInDto;
import com.tweetbox.account.services.OAuthService;
import com.tweetbox.api.data.OAuthRes;
import lombok.SneakyThrows;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("oauth")
public class OAuthController {
  private final OAuthService oauthService;

  public OAuthController(OAuthService OAuthService) {
    this.oauthService = OAuthService;
  }


  @GetMapping("sign-up")
  public OAuthRes SignUp(HttpServletRequest request) {
    OAuthRes ret = this.oauthService.SignUp();
    HttpSession session = request.getSession();

    session.setAttribute("oauth_token", ret.getOauth_token());
    session.setAttribute("oauth_token_secret", ret.getOauth_token_secret());

    return ret;
  }

  @SneakyThrows
  @GetMapping("callback")
  public OAuthRes CallBack(HttpServletRequest request, ReqSignInDto reqSignInDto) {
    HttpSession session = request.getSession();

    Object oauth_token_secret = session.getAttribute("oauth_token_secret");
    Object oauth_token = session.getAttribute("oauth_token");

    if (oauth_token == null || oauth_token_secret == null) {
      throw new BadHttpRequest();
    }

    reqSignInDto.setOauth_token_secret(oauth_token_secret.toString());
    reqSignInDto.setOauth_token(oauth_token.toString());


    OAuthRes ret = this.oauthService.CallBack(reqSignInDto);
    session.setAttribute("OAuth", ret);

    return ret;
  }

}
