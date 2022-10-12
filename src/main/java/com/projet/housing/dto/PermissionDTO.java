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
    private Boolean checked;

    public PermissionDTO(String id, String code, Boolean checked) {
        this.id = id;
        this.code = code;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
