package com.finwise.api.repositories;

import com.finwise.api.models.Account;
import com.finwise.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    List<Account> findByUserIdAndActiveIsTrue(Long userId);
}