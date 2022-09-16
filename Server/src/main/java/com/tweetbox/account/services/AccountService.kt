package com.tweetbox.account.services

import com.tweetbox.account.dao.AccountDaoImpl
import com.tweetbox.account.entities.Account

class AccountService(private var accountDaoImpl: AccountDaoImpl) {
  fun SaveAccount(account: Account): Account {
    return this.accountDaoImpl.saveAccount(account);
  }
}