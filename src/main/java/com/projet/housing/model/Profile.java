/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lerusse
 */
@Entity
public class Profile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false, length = 50)
    private String id;
    
    @Column(nullable = false)
    @NotBlank(message = "Le code est obligatoire")
    private String code;
    
    @Column(nullable = false)
    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
    
    @Column(nullable = false)
    @NotBlank(message = "La description est obligatoire")
    private String description;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_permission", joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<Permission> permissions;

    public Profile(String code, String libelle, String description, Set<Permission> permissions) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.permissions = permissions;
    }

    public Profile(String code, String libelle, String description) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
    }
    
    public Profile() {
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Profile{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", description=" + description + ", permissions=" + permissions + '}';
    }    
}
