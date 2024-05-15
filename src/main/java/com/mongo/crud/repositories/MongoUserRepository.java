package com.mongo.crud.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.crud.entities.AppUser;

@Repository
public interface MongoUserRepository extends MongoRepository<AppUser, String> {
}
