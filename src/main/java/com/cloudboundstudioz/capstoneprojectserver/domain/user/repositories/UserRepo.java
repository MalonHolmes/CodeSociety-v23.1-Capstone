package com.cloudboundstudioz.capstoneprojectserver.domain.user.repositories;

import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
