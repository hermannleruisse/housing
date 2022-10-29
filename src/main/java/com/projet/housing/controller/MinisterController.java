package com.projet.housing.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.housing.db.MinisterRepository;
import com.projet.housing.dto.ApiError;
import com.projet.housing.dto.MinisterDTO;
import com.projet.housing.model.Minister;
import com.projet.housing.service.MinisterService;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
@RequestMapping("/api/manager")
@CrossOrigin
public class MinisterController {
    @Autowired
    private MinisterService ministerService;
    
    @Autowired
    private MinisterRepository ministerRepository;
    
    @Autowired
    private Environment environment;

    /**
     * Create - Add a new minister
     *
     * @param minister An object minister
     * @return The minister object saved
     */
    /*@PreAuthorize("hasAuthority('PM_ADD_MI')")*/
    @PostMapping("/save-minister")
    public Object createMinister(@Valid @RequestBody Minister minister) {
        Optional<Minister> p = this.ministerRepository.checkIfMinisterExistByCode(minister.getCode());
        if (p.isPresent()) {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    environment.getProperty("unique.code"), environment.getProperty("unique.code"));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
        return ministerService.saveMinister(minister);
    }

    /**
     * Read - Get one minister
     *
     * @param id The id of the minister
     * @return An Minister object full filled
     */
    @GetMapping("/minister/{id}")
    public Minister getMinister(@PathVariable("id") final String id) {
        Optional<Minister> minister = ministerService.getMinister(id);
        if (minister.isPresent()) {
            return minister.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all ministers
     *
     * @return - An Iterable object of Minister full filled
     */
    @GetMapping("/ministers")
    public Iterable<Minister> getMinisters() {
        return ministerService.getMinisters();
    }

    /**
     * Update - Update an existing minister
     *
     * @param id - The id of the minister to update
     * @param minister - The minister object updated
     * @return
     */
    /*@PreAuthorize("hasAuthority('PM_EDI_MI')")*/
    @PutMapping("/edit-minister/{id}")
    public Minister updateMinister(@PathVariable("id") final String id, @Valid @RequestBody MinisterDTO minister) {
        Optional<Minister> e = ministerService.getMinister(id);
        if (e.isPresent()) {
            Minister currentMinister = e.get();
            
            String code = minister.getCode();
            if (code != null) {
                currentMinister.setCode(code);
            }
            String libelle = minister.getLibelle();
            if (libelle != null) {
                currentMinister.setLibelle(libelle);
            }
            String description = minister.getDescription();
            if (description != null) {
                currentMinister.setDescription(description);
            }
            
            ministerService.saveMinister(currentMinister);
            return currentMinister;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an minister
     *
     * @param id - The id of the minister to delete
     */
    //@PreAuthorize("hasAuthority('PM_DEL_MI')")
    @DeleteMapping("/delete-minister/{id}")
    public void deleteMinister(@PathVariable("id") final String id) {
        ministerService.deleteMinister(id);
    }
}
