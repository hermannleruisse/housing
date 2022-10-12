/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.service;

import com.projet.housing.db.PermissionRepository;
import com.projet.housing.model.Permission;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lerusse
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Optional<Permission> getPermission(final String id) {
        return permissionRepository.findById(id);
    }

    public Iterable<Permission> getPermissions() {
        return permissionRepository.findAll();
    }
}
