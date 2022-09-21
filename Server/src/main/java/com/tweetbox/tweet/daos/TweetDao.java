package com.tweetbox.tweet.daos;

import com.tweetbox.tweet.entities.Tweet;

import java.util.ArrayList;

public interface TweetDao {
  Tweet SaveTweet(Tweet tweet);

  ArrayList<Tweet> SaveTweet(ArrayList<Tweet> listTweet);
}
