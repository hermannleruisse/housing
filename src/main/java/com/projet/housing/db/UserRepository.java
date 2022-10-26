/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.db;

import com.projet.housing.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lerusse
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
    
    @Query(value = "SELECT * FROM user u WHERE u.username = ?1", nativeQuery = true)
    Optional<User> checkIfUserExistByUsername(String username);

    @Query(value = "INSERT INTO user(active, password, profile_id, username) values (?1, ?2, ?3, ?4)", nativeQuery = true)
    void addUser(int active, String password, String profileId, String username);
}
