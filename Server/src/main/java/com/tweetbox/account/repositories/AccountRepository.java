package com.tweetbox.account.repositories;

import com.tweetbox.account.entities.Account;
import com.tweetbox.progress.entities.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
