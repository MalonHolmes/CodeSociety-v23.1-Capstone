package com.cloudboundstudioz.capstoneprojectserver.domain.profile.controllers;

import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // POST requests
    @PostMapping
    public ResponseEntity<Profile> create(@RequestBody Profile profile){
        profile = profileService.create(profile);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    // GET requests
    @GetMapping("{id}")
    public ResponseEntity<Profile> getById(@PathVariable("id") Long id) {
        Profile profile = profileService.getById(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // PUT requests
    @PutMapping("{id}")
    public ResponseEntity<Profile> update(@PathVariable("id") Long id, @RequestBody Profile profileDetail){
        profileDetail = profileService.update(id, profileDetail);
        return new ResponseEntity<>(profileDetail, HttpStatus.ACCEPTED);
    }

    // DELETE requests
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        profileService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
