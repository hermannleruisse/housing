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
public class PermissionDTO {
    private String id;
    private String code;
    private String libelle;
    private Boolean checked;

    public PermissionDTO(String id, String code, Boolean checked) {
        this.id = id;
        this.code = code;
        this.checked = checked;
    }
    
    public PermissionDTO(String id, String code, String libelle, Boolean checked) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.checked = checked;
    }
    
    public PermissionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +"id=" + id +  "code=" + code +  "libelle=" + libelle + ", checked=" + checked + '}';
    }
}
