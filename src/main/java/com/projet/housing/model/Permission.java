/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lerusse
 */
@Entity
public class Permission implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false, unique = true, length = 50)
    private String id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Le code est obligatoire")
    private String code;
    
    @Column(nullable = false)
    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
    
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    Set<Profile> profile;

    public Permission() {
    }

    public Permission(String id, String code, String libelle, Menu menu) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.menu = menu;
    }

    public Permission(String code, String libelle, Menu menu) {
        this.code = code;
        this.libelle = libelle;
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Set<Profile> getProfile() {
        return profile;
    }

    public void setProfile(Set<Profile> profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Permission{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", menu=" + menu + ", profile=" + profile + '}';
    }
}
