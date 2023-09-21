package com.cloudboundstudioz.capstoneprojectserver.domain.profile.repositories;

import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String name);
}