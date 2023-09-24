package com.cloudboundstudioz.capstoneprojectserver.domain.profile.services;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.repositories.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImplementation implements ProfileService {

    private ProfileRepo profileRepo;

    @Autowired
    public ProfileServiceImplementation(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    // CREATE methods
    @Override
    public Profile create(Profile profile) throws ResourceCreationException {
        Optional<Profile> optional = profileRepo.findByName(profile.getName());
        if (optional.isPresent())
            throw new ResourceCreationException("Profile already exists");
        profile = profileRepo.save(profile);
        return profile;
    }

    // READ methods
    @Override
    public Profile getById(Long id) throws ResourceNotFoundException {
        Profile profile = profileRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Profile with id: " + id));
        return profile;
    }

    @Override
    public Profile getByName(String name) throws ResourceNotFoundException {
        Profile profile = profileRepo.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("No Profile with name: " + name));
        return profile;
    }

    // UPDATE methods
    @Override
    public Profile update(Long id, Profile profileDetails) throws ResourceNotFoundException {
        Profile profile = getById(id);
        profile.setName(profileDetails.getName());
        profile.setAge(profileDetails.getAge());
        profile.setHeight(profileDetails.getHeight());
        profile.setWeight(profileDetails.getWeight());
        return profile;
    }

    // DELETE methods
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Profile profile = getById(id);
        profileRepo.delete(profile);
    }
}
