package com.sachin.UserAPI.Repository;

import com.sachin.UserAPI.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

}
