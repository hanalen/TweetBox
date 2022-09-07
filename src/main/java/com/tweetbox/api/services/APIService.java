package com.tweetbox.api.services;

import com.tweetbox.TweetBoxConfig;
import com.tweetbox.api.dtos.APIRequest;
import com.tweetbox.api.data.OAuth;
import com.tweetbox.api.data.OAuthRes;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

@Component
public class APIService {
  private final TweetBoxConfig tweetBoxConfig;

  public APIService(TweetBoxConfig tweetBoxConfig) {
    this.tweetBoxConfig = tweetBoxConfig;
  }

  private void SetHeader(HttpURLConnection connection, String authorization, @Nullable() String contentType) {

    if (contentType != null) {
      connection.setRequestProperty("Content-Type", contentType);
    } else {
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;encoding=utf-8");
    }
    connection.setRequestProperty("Authorization", authorization);
  }

  @SneakyThrows
  public OAuthRes RequestOAuth(APIRequest apiRequest) {
    String urlEndpoint = apiRequest.getUrl();
    OAuth oauth = new OAuth(new TreeMap<>(), tweetBoxConfig);
    oauth.setKey("", "");
    String body = apiRequest.getData().size() > 0 ? oauth.createBody(apiRequest) : "";
    String reqUrl = oauth.getUrl(apiRequest, urlEndpoint, false);

    URL url = new URL(reqUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;encoding=utf-8");
    String headers = oauth.getHeader(apiRequest, "POST", urlEndpoint);
    this.SetHeader(connection, headers, urlEndpoint);

    BufferedReader bufferedReader = null;
    try {
      connection.getResponseCode();
      bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append('\r');
      }

      String response = stringBuilder.toString();
      String[] splitResponse = response.split("&");
      OAuthRes oAuthRes = new OAuthRes();
      for (int i = 0; i < splitResponse.length; i++) {
        String[] strs = splitResponse[i].split("=");
        if (strs[0].equals("oauth_callback_confirmed")) {
          oAuthRes.setOauth_callback_confirmed(strs[1] == "true");
        } else if (strs[0].equals("oauth_token")) {
          oAuthRes.setOauth_token(strs[1]);
        } else if (strs[0].equals(("oauth_token_secret"))) {
          oAuthRes.setOauth_token_secret(strs[1]);
        } else if (strs[0].equals("screen_name")) {
          oAuthRes.setScreen_name(strs[1]);
        } else if (strs[0].equals("name")) {
          oAuthRes.setName(strs[1]);
        } else if (strs[0].equals("user_id")) {
          oAuthRes.setUser_id(strs[1]);
        }
      }
      return oAuthRes;
    } catch (Exception e) {
      bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append('\r');
      }

      throw e;
    } finally {
      bufferedReader.close();
    }
  }
}
