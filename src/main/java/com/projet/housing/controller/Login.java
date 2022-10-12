/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.controller;

import com.projet.housing.db.UserRepository;
import com.projet.housing.dto.LoginViewModel;
import com.projet.housing.dto.UserDTO;
import com.projet.housing.model.User;
import com.projet.housing.security.JwtProperties;
import com.projet.housing.security.UserPrincipalDetailsService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lerusse
 */

@RestController
@RequestMapping("/")
public class Login {

    @Autowired
    private UserPrincipalDetailsService upds;
    
    @CrossOrigin(exposedHeaders = JwtProperties.HEADER_STRING)
    @PostMapping("login")
    public UserDetails connexion(@Valid @RequestBody LoginViewModel loginViewModel){
        return this.upds.loadUserByUsername(loginViewModel.getUsername());
    }
    
//    @PostMapping("login")
//    public ResponseEntity<?> getUser(LoginViewModel loginViewModel){
//        Map<String, Object> user = new HashMap<>();
//        user.put("name", "toto");
//        user.put("city", "togo");
//        return ResponseEntity.ok(user);
//    }
    
    @PostMapping("/users")
    ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        // persisting the user
        return ResponseEntity.ok("User is valid");
    }
}
