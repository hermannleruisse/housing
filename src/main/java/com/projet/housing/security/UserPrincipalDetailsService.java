/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.security;

import com.projet.housing.db.UserRepository;
import com.projet.housing.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author lerusse
 */
@Service
public class UserPrincipalDetailsService implements UserDetailsService{
    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        User u = this.userRepository.findByUsername(string);
        UserPrincipal userPrincipal = new UserPrincipal(u);
        return userPrincipal;
    }
}
