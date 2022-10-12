/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.service;

import com.projet.housing.db.ProfileRepository;
import com.projet.housing.model.Profile;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lerusse
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Optional<Profile> getProfile(String id) {
        return profileRepository.findById(id);
    }
    
    public Iterable<Profile> getProfiles() {
        return profileRepository.findAll();
    }

    public void deleteProfile(String id) {
        profileRepository.deleteById(id);
    }

    public Profile saveProfile(Profile profile) {
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile;
    }
}
