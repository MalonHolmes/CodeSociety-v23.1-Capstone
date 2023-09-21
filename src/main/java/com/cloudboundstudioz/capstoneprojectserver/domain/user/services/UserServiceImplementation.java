package com.cloudboundstudioz.capstoneprojectserver.domain.user.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.User;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImplementation(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    // CREATE methods
    @Override
    public User create(User user) {
        Optional<User> optionalUsername = userRepo.findByUsername(user.getUsername());
        Optional<User> optionalEmail = userRepo.findByEmail(user.getEmail());
        if (optionalUsername.isPresent())
            throw new ResourceCreationException("User with username exists: " + user.getUsername());
        else if (optionalEmail.isPresent())
            throw new ResourceCreationException("User with email exists: " + user.getEmail());
        user = userRepo.save(user);
        return user;
    }

    // READ methods
    @Override
    public User getById(Long id) throws ResourceNotFoundException {
        User user = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No User with id: " + id));
        return user;
    }

    @Override
    public User getByEmail(String email) throws ResourceNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("No User with email: " + email));
        return user;
    }

    @Override
    public User getByUsername(String username) throws ResourceNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("No User with username: " + username));
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    // UPDATE methods

    @Override
    public User updateUser(Long id, User newUser) throws ResourceNotFoundException {
        User user = getById(id);
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setProfile(newUser.getProfile());
        return user;
    }

    @Override
    public void deleteUser(Long id){
        User user = getById(id);
        userRepo.delete(user);
    }
}
