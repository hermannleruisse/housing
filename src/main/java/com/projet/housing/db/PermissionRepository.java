/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.db;

import com.projet.housing.model.Permission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lerusse
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{
    @Query(value = "SELECT * FROM permission p WHERE p.code = ?1", nativeQuery = true)
    Optional<Permission> checkIfPermissionExistByCode(String scode);
}