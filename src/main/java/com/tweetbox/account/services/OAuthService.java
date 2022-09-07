package com.tweetbox.account.services;

//import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
//import org.springframework.security.*;

import com.tweetbox.account.dtos.ReqSignInDto;
import com.tweetbox.api.data.OAuthRes;
import com.tweetbox.api.dtos.ReqAccessToken;
import com.tweetbox.api.dtos.ReqOAuthToken;
import com.tweetbox.api.services.APIService;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {
  private final APIService apiService;

  public OAuthService(APIService apiService) {
    this.apiService = apiService;
  }

  public OAuthRes SignUp() {
    ReqOAuthToken request = new ReqOAuthToken();
    return this.apiService.RequestOAuth(request);
  }

  public OAuthRes CallBack(ReqSignInDto reqSignInDto) {
    ReqAccessToken reqAccessToken = new ReqAccessToken();
    String oauthToken = reqSignInDto.getOauth_token();
    String secretToken = reqSignInDto.getOauth_token_secret();
    reqAccessToken.setOauth_verifier(reqSignInDto.getOauth_verifier());

    reqAccessToken.setOauth_token(reqSignInDto.getOauth_token());
    reqAccessToken.setUser_secret_key(reqSignInDto.getOauth_token_secret());

    return this.apiService.RequestOAuth(reqAccessToken);
  }
}
