/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.dto;

/**
 *
 * @author lerusse
 */
public class CheckAuthorityDTO {
    private String profile;
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permissions) {
        this.permission = permissions;
    }

    public CheckAuthorityDTO(String profile, String permission) {
        this.profile = profile;
        this.permission = permission;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
