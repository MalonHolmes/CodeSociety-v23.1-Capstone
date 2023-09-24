package com.cloudboundstudioz.capstoneprojectserver.domain.user.repositories;

import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
}
