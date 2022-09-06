package com.tweetbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"appkey.properties"})
public class TweetBoxConfig {
  @Value("${consumerKey}")
  private String consumerKey;

  @Value("${consumerSecretKey}")
  private String consumerSecretKey;

  @Value("${callbackUrl}")
  private String callbackUrl;

  public String getConsumerKey() {
    return this.consumerKey;
  }

  public String getConsumerSecretKey() {
    return this.consumerSecretKey;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }
}
