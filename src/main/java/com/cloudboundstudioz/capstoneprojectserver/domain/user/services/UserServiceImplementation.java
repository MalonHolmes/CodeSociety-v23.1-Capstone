package com.cloudboundstudioz.capstoneprojectserver.domain.user.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
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
    public UserModel create(UserModel userModel) {
        Optional<UserModel> optionalUsername = userRepo.findByUsername(userModel.getUsername());
        Optional<UserModel> optionalEmail = userRepo.findByEmail(userModel.getEmail());
        if (optionalUsername.isPresent())
            throw new ResourceCreationException("User with username exists: " + userModel.getUsername());
        else if (optionalEmail.isPresent())
            throw new ResourceCreationException("User with email exists: " + userModel.getEmail());
        userModel = userRepo.save(userModel);
        return userModel;
    }

    // READ methods
    @Override
    public UserModel getById(Long id) throws ResourceNotFoundException {
        UserModel userModel = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No User with id: " + id));
        return userModel;
    }

    @Override
    public UserModel getByEmail(String email) throws ResourceNotFoundException {
        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("No User with email: " + email));
        return userModel;
    }

    @Override
    public UserModel getByUsername(String username) throws ResourceNotFoundException {
        UserModel userModel = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("No User with username: " + username));
        return userModel;
    }

    @Override
    public List<UserModel> getAll() {
        return userRepo.findAll();
    }

    // UPDATE methods

    @Override
    public UserModel updateUser(Long id, UserModel newUserModel) throws ResourceNotFoundException {
        UserModel userModel = newUserModel;
        return userModel;
    }

    @Override
    public void deleteUser(Long id){
        UserModel userModel = getById(id);
        userRepo.delete(userModel);
    }
}
