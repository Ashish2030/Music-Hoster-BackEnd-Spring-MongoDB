package com.trail.musicalhost.repository;

import com.trail.musicalhost.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
}
