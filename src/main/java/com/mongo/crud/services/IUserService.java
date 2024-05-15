package com.mongo.crud.services;

import org.springframework.stereotype.Service;

import com.mongo.crud.entities.AppUser;

@Service
public interface IUserService {
    AppUser createUser(AppUser user);

    AppUser getUserById(String userId);

    AppUser updateUser(String userId, AppUser updateUser);

    boolean deleteUser(String userId);
}
