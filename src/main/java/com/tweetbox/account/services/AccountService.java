package com.tweetbox.account.services;

//import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
//import org.springframework.security.*;

import com.tweetbox.api.dtos.APIRequest;
import com.tweetbox.api.data.OAuthRes;
import com.tweetbox.api.dtos.ReqOAuthToken;
import com.tweetbox.api.services.APIService;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class AccountService {
  private final APIService apiService;

  public AccountService(APIService apiService) {
    this.apiService = apiService;
  }

  public OAuthRes SignUp() {
    ReqOAuthToken request = new ReqOAuthToken();
    return this.apiService.RequestOAuth(request);
  }
}
