/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lerusse
 */
@Entity
@Table(name = "APP_USER")
public class User implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false, length = 50)
    private String id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
    
    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe des obligatoire")
    private String password;
    
    @Column(nullable = false)
    private int active = 1;

    private String token;
    
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @NotNull(message = "Le profile est obligatoire")
    private Profile profile;
    
    public User(String username, String password, Profile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public User(String username, String password, int active, Profile profile, String token) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.profile = profile;
        this.token = token;
    }

    public User() {
    }

    public String getId() {
        return id;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public List<String> getRoleList() {
//        if (this.roles.length() > 0) {
//            return Arrays.asList(this.roles.split(","));
//        }
//        return new ArrayList<>();
//    }
    
    public String getRole() {
        if (this.profile != null) {
            return "ROLE_"+this.profile.getCode();
        }
        return new String();
    }
    
    public List<String> getPermissionList() {
        List<String> listP = new ArrayList<>();
        if (this.profile.permissions != null) {
            this.profile.permissions.forEach(p ->{
                listP.add(p.getCode());
            });
            return listP;
        }
        return new ArrayList<>();
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
