package com.tweetbox.account.controllers;

import com.tweetbox.account.services.AccountService;
import com.tweetbox.api.data.OAuthRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("account")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }


  @GetMapping("sign-up")
  public OAuthRes SignUp() {
    return this.accountService.SignUp();
  }


}
