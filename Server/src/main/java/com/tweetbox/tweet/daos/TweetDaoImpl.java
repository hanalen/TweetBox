package com.tweetbox.tweet.daos;

import com.tweetbox.tweet.entities.Tweet;
import com.tweetbox.tweet.repositories.TweetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TweetDaoImpl implements TweetDao {
  private final TweetRepository tweetRepository;

  public TweetDaoImpl(TweetRepository tweetRepository) {
    this.tweetRepository = tweetRepository;
  }

  @Override
  public Tweet SaveTweet(Tweet tweet) {
    return this.tweetRepository.save(tweet);
  }

  @Override
  public ArrayList<Tweet> SaveTweet(ArrayList<Tweet> listTweet) {
    return (ArrayList<Tweet>) this.tweetRepository.saveAll(listTweet);
  }
}
