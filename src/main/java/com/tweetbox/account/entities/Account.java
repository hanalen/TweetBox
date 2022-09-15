package com.tweetbox.account.entities;

import com.tweetbox.progress.entities.ProgressStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Account {
  @Id
  @GeneratedValue
  public Long id;

  @Column(nullable = false)
  public Long userId;
  
  @Column(nullable = false)
  public String oauth_token;

  @Column(nullable = false)
  public String user_secret_key;
}
