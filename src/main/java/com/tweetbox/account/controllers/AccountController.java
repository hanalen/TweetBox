package com.tweetbox.account.controllers;

import com.tweetbox.account.dtos.ReqSignInDto;
import com.tweetbox.account.services.AccountService;
import com.tweetbox.api.data.OAuthRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("account")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }


  @GetMapping("sign-up")
  public OAuthRes SignUp(HttpServletRequest request) {
    OAuthRes ret = this.accountService.SignUp();
    HttpSession session = request.getSession();

    session.setAttribute("oauth_token", ret.getOauth_token());
    session.setAttribute("oauth_token_secret", ret.getOauth_token_secret());

    return ret;
  }

  @GetMapping("sign-in")
  public OAuthRes SignIn(HttpServletRequest request, ReqSignInDto reqSignInDto) {
    HttpSession session = request.getSession();
    reqSignInDto.setOauth_token_secret(session.getAttribute("oauth_token_secret").toString());
    reqSignInDto.setOauth_token(session.getAttribute("oauth_token").toString());

    return this.accountService.SignIn(reqSignInDto);
  }

}
