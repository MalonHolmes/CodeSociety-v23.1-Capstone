package com.cloudboundstudioz.capstoneprojectserver.domain.profile.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;

public interface ProfileService {
    Profile create(Profile profile) throws ResourceCreationException;
    Profile getById(Long id) throws ResourceNotFoundException;
    Profile update(Long id, Profile profile) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
