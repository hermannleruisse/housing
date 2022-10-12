/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.db;

import com.projet.housing.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lerusse
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{

}