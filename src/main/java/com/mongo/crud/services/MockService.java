package com.mongo.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.mongo.crud.entities.AppUser;
import com.mongo.crud.repositories.MongoUserRepository;

@Service
@Profile("test")
public class MockService implements IUserService{

    @MockBean
    private MongoUserRepository mockUserRepository;

    @Autowired
    public MockService(MongoUserRepository mockUserRepository) {
        this.mockUserRepository = mockUserRepository;
    }


    @Override
    public AppUser createUser(AppUser user) {
        return mockUserRepository.save(user);
    }

    @Override
    public AppUser getUserById(String userId) {
       return mockUserRepository.findById(userId).orElse(null);
    }

    @Override
    public AppUser updateUser(String userId, AppUser updateUser) {
        AppUser user = mockUserRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(updateUser.getUsername());
            user.setDob(updateUser.getDob());
            return mockUserRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        try {
            mockUserRepository.deleteById(userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
