/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lerusse
 */
public class ProfileDTO {
    
    @NotBlank(message = "Le code est obligatoire")
    private String code;
    
    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;
    
    @NotBlank(message = "La description est obligatoire")
    private String description;

    public ProfileDTO(String code, String libelle, String description) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
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

    @Override
    public String toString() {
        return "ProfileDTO{" + "code=" + code + ", libelle=" + libelle + ", description=" + description + '}';
    }
}
