package com.mongo.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongo.crud.entities.AppUser;

@Service
@Transactional
public class MongoUserService implements IUserService {

    @Autowired
    private MongoRepository userRepository;
    @Override
    public AppUser createUser(AppUser user) {
        return (AppUser) userRepository.save(user);
    }

    @Override
    public AppUser getUserById(String userId) {
        return (AppUser) userRepository.findById(userId).get();
    }

    @Override
    public AppUser updateUser(String userId, AppUser updateUser) {
        AppUser user = (AppUser)userRepository.findById(userId).get();
        user.setUsername(updateUser.getUsername());
        user.setDob(updateUser.getDob());
        return (AppUser) userRepository.save(user);
    }

    @Override
    public boolean deleteUser(String userId) {
         userRepository.deleteById(userId);
         return true;
    }
}
