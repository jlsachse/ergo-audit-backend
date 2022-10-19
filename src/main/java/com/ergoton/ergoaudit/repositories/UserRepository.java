package com.ergoton.ergoaudit.repositories;

import com.ergoton.ergoaudit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'walletAddress': '?0' }")
    Optional<User> findByWalletAddress(String walletAddress);

}
