/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.dto;

import java.util.List;

/**
 *
 * @author lerusse
 */
public class HabilitationDTO {
    private String profile;
    private List<PermissionDTO> permissions;

    public HabilitationDTO(String profile, List<PermissionDTO> permissions) {
        this.profile = profile;
        this.permissions = permissions;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
