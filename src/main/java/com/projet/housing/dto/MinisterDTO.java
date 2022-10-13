package com.projet.housing.dto;

import javax.validation.constraints.NotBlank;

public class MinisterDTO {
    @NotBlank(message = "Le code est obligatoire")
    private String code;
    
    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;
    
    @NotBlank(message = "La description est obligatoire")
    private String description;

    /**
     * @return String return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return String return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
}
