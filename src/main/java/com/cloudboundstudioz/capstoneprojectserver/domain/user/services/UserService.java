package com.cloudboundstudioz.capstoneprojectserver.domain.user.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.User;

import java.util.List;

public interface UserService {
    User create(User user) throws ResourceCreationException;
    User getById(Long id) throws ResourceNotFoundException ;
    User getByEmail(String email) throws ResourceNotFoundException ;
    User getByUsername(String username) throws ResourceNotFoundException ;
    List<User> getAll();
    User updateUser(Long id, User newUser) throws ResourceNotFoundException;
    void deleteUser(Long id);
}
