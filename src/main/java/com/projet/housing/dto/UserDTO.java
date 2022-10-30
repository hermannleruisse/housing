/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author lerusse
 */
public class UserDTO {
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
    
    
//    @NotNull(message = "Le profile est requis")
    @NotEmpty(message = "Le profile est obligatoire")
    private String profile;

    public UserDTO(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public UserDTO(String nom, String prenom, String username, String password, String profile) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "UserDTO{" +"nom=" + nom +  "prenom=" + prenom +  "username=" + username + ", password=" + password + ", profile=" + profile + '}';
    }
    
}
