package com.tweetbox.account.dao;

import com.tweetbox.account.entities.Account;
import com.tweetbox.account.repositories.AccountRepository;
import com.tweetbox.progress.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDaoImpl implements AccountDao {
  private final AccountRepository accountRepository;

  @Autowired
  AccountDaoImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account saveAccount(Account account) {
    return this.accountRepository.save(account);
  }
}
