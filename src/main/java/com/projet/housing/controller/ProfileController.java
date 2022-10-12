/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.controller;

import com.projet.housing.db.ProfileRepository;
import com.projet.housing.dto.ApiError;
import com.projet.housing.dto.ProfileDTO;
import com.projet.housing.model.Profile;
import com.projet.housing.service.ProfileService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lerusse
 */
@RestController
@RequestMapping("/api/security")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private Environment environment;
    
    @RequestMapping("/profiles__")
    public ResponseEntity<?> listProfiles() {
        Map<String, Object> profile = new HashMap<>();
        profile.put("name", "toto");
        return ResponseEntity.ok(profile);
    }
    
    /**
     * Create - Add a new profile
     *
     * @param profile An object profile
     * @return The profile object saved
     */
    @PostMapping("/save-profile")
    public Object createProfile(@Valid @RequestBody Profile profile) {
        Optional<Profile> p = this.profileRepository.checkIfProfileExistByCode(profile.getCode());
        if (p.isPresent()) {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    environment.getProperty("unique.code"), environment.getProperty("unique.code"));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
        return profileService.saveProfile(profile);
    }

    /**
     * Read - Get one profile
     *
     * @param id The id of the profile
     * @return An Profile object full filled
     */
    @GetMapping("/profile/{id}")
    public Profile getProfile(@PathVariable("id") final String id) {
        Optional<Profile> profile = profileService.getProfile(id);
        if (profile.isPresent()) {
            return profile.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all profiles
     *
     * @return - An Iterable object of Profile full filled
     */
    @GetMapping("/profiles")
    public Iterable<Profile> getProfiles() {
        return profileService.getProfiles();
    }

    /**
     * Update - Update an existing profile
     *
     * @param id - The id of the profile to update
     * @param profile - The profile object updated
     * @return
     */
    @PutMapping("/edit-profile/{id}")
    public Profile updateProfile(@PathVariable("id") final String id, @Valid @RequestBody ProfileDTO profile) {
        Optional<Profile> e = profileService.getProfile(id);
        if (e.isPresent()) {
            Profile currentProfile = e.get();
            
            String code = profile.getCode();
            if (code != null) {
                currentProfile.setCode(code);
            }
            String libelle = profile.getLibelle();
            if (libelle != null) {
                currentProfile.setLibelle(libelle);
            }
            String description = profile.getDescription();
            if (description != null) {
                currentProfile.setDescription(description);
            }
            
            profileService.saveProfile(currentProfile);
            return currentProfile;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an profile
     *
     * @param id - The id of the profile to delete
     */
    @DeleteMapping("/delete-profile/{id}")
    public void deleteProfile(@PathVariable("id") final String id) {
        profileService.deleteProfile(id);
    }
}
