/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.controller;

import com.projet.housing.db.UserRepository;
import com.projet.housing.dto.ApiError;
import com.projet.housing.dto.UserDTO;
import com.projet.housing.model.Profile;
import com.projet.housing.model.User;
import com.projet.housing.service.ProfileService;
import com.projet.housing.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserController {

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @RequestMapping("/liste-des-utilisateurs")
    public List<User> listUtilisateur() {
        return this.uRepository.findAll();
    }

    /**
     * Create - Add a new user
     *
     * @param user An object user
     * @return The user object saved
     */
    @PostMapping("/save-user")
    public Object createUser(@Valid @RequestBody UserDTO user) {
        Optional<User> us = uRepository.checkIfUserExistByUsername(user.getUsername());
        if (us.isPresent()) {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    environment.getProperty("unique.username"), environment.getProperty("unique.username"));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        } else {
            Optional<Profile> profile = profileService.getProfile(user.getProfile());
            if (profile.isPresent()) {
                User u = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), profile.get());
                return userService.saveUser(u);
            }
        }
        return null;
    }

    /**
     * Read - Get one user
     *
     * @param id The id of the user
     * @return An User object full filled
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") final String id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all users
     *
     * @return - An Iterable object of User full filled
     */
    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * Update - Update an existing user
     *
     * @param id - The id of the user to update
     * @param user - The user object updated
     * @return
     */
    @PutMapping("/edit-user/{id}")
    public User updateUser(@PathVariable("id") final String id, @Valid @RequestBody UserDTO user) {
        Optional<User> e = userService.getUser(id);
        if (e.isPresent()) {
            User currentUser = e.get();

            String username = user.getUsername();
            if (username != null) {
                currentUser.setUsername(username);
            }
            String password = user.getPassword();
            if (password != null) {
                currentUser.setPassword(password);
            }
            String profile = user.getProfile();
            if (profile != null) {
                Optional<Profile> p = profileService.getProfile(user.getProfile());
                currentUser.setProfile(p.get());
            }
            userService.saveUser(currentUser);
            return currentUser;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an user
     *
     * @param id - The id of the user to delete
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") final String id) {
        userService.deleteUser(id);
    }

}
