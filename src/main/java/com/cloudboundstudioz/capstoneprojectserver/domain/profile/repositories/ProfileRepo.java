package com.cloudboundstudioz.capstoneprojectserver.domain.profile.repositories;

import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String name);
}