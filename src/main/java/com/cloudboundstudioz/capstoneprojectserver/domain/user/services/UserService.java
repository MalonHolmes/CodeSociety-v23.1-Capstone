package com.cloudboundstudioz.capstoneprojectserver.domain.user.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;

import java.util.List;

public interface UserService {
    UserModel create(UserModel userModel) throws ResourceCreationException;
    UserModel getById(Long id) throws ResourceNotFoundException ;
    UserModel getByEmail(String email) throws ResourceNotFoundException ;
    UserModel getByUsername(String username) throws ResourceNotFoundException ;
    List<UserModel> getAll();
    UserModel updateUser(Long id, UserModel newUserModel) throws ResourceNotFoundException;
    void deleteUser(Long id);
}
