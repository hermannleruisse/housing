/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.db;

import com.projet.housing.model.Profile;
import java.awt.print.Pageable;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lerusse
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, String>{
    @Query("SELECT p FROM Profile p WHERE p.id = 1")
    Collection<Profile> findAllProfile();
    
    @Query(value = "SELECT * FROM profile p WHERE p.id = 1", nativeQuery = true)
    Collection<Profile> findAllProfileNative();
    
    @Query(value = "SELECT * FROM profile p WHERE p.code = ?1", nativeQuery = true)
    Optional<Profile> checkIfProfileExistByCode(String scode);
    
    //Pagination
//    @Query(value = "SELECT p FROM Profile u ORDER BY id")
//    Page<Profile> findAllProfileWithPagination(Pageable pageable);
//    
//    @Query(
//    value = "SELECT * FROM Users ORDER BY id", 
//    countQuery = "SELECT count(*) FROM Users", 
//    nativeQuery = true)
//    Page<Profile> findAllProfileWithPaginationNative(Pageable pageable);
}
