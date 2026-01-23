package com.mihirrueben.Recipe_app.repository;

import com.mihirrueben.Recipe_app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //custom query to find a user by username for Login
    Optional<User> findByUsername(String username);

    //checking if email exists during registration
    Boolean existsByEmail(String email);
}