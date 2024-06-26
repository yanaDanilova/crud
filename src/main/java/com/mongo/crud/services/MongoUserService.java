package com.mongo.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongo.crud.entities.AppUser;

@Service
@Transactional
@Profile("prod")
public class MongoUserService implements IUserService {

    private MongoRepository<AppUser,String> userRepository;

    @Autowired
    public MongoUserService(MongoRepository<AppUser, String> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public AppUser getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public AppUser updateUser(String userId, AppUser updateUser) {
        AppUser user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(updateUser.getUsername());
            user.setDob(updateUser.getDob());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
