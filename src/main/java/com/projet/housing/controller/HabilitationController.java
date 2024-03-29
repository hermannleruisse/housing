/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.controller;

import com.projet.housing.db.UserRepository;
import com.projet.housing.dto.HabilitationDTO;
import com.projet.housing.dto.PermissionDTO;
import com.projet.housing.model.Permission;
import com.projet.housing.model.Profile;
import com.projet.housing.model.User;
import com.projet.housing.service.MenuService;
import com.projet.housing.service.PermissionService;
import com.projet.housing.service.ProfileService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lerusse
 */
@RestController
@RequestMapping("/api/security")
@CrossOrigin
public class HabilitationController {

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    /**
     * since 06/08/2021 retourne les menu pour les permissions
     *
     * @param profileId
     * @return
     */
    @GetMapping(value = {"/liste-habilitation", "/liste-habilitation/{profileId}"})
//    @ResponseBody
    public ResponseEntity<?> listHabilitations(@PathVariable(required = false) String profileId) {
        List<Object> listH = new ArrayList<>();
        menuService.getMenus().forEach(m -> {
            Map<String, Object> habilitation = new HashMap<>();
            List<PermissionDTO> listP = new ArrayList<>();
            permissionService.getPermissions().forEach(p -> {
                if (m.equals(p.getMenu())) {
                    if (profileId == null) {
                        listP.add(new PermissionDTO(p.getId(), p.getCode(), p.getLibelle(), Boolean.FALSE));
                    } else {
                        Optional<Profile> prof = profileService.getProfile(profileId);
                        if (prof.isPresent()) {
                        
                            Profile currentProfile = prof.get();
                            if (!currentProfile.getPermissions().isEmpty()) {

                                currentProfile.getPermissions().forEach(x -> {
                                    if(p.equals(x)){
                                        listP.add(new PermissionDTO(p.getId(), p.getCode(), p.getLibelle(), Boolean.TRUE));
                                    }else{
                                        listP.add(new PermissionDTO(p.getId(), p.getCode(), p.getLibelle(), Boolean.FALSE));
                                    }
                                });
                            }else{
                                listP.add(new PermissionDTO(p.getId(), p.getCode(), p.getLibelle(), Boolean.FALSE));
                            }
                        }
                    }
                    habilitation.put("permissions", listP);
                } else {
                    habilitation.put("permissions", listP);
                }
            });
            habilitation.put("menu", m.getLibelle());
            listH.add(habilitation);
        });
        return ResponseEntity.ok(listH);
    }

    /**
     * enregistre les habilitations
     *
     * @param h
     * @return
     */
    @PreAuthorize("hasAuthority('PM_EDI_H')")
    @PostMapping("save-habilitation")
    public ResponseEntity<?> saveHabilitation(@RequestBody HabilitationDTO h) {
        System.out.println("HabilitationDTO "+h.toString());
        Optional<Profile> p = profileService.getProfile(h.getProfile());
        List<PermissionDTO> listPM = h.getPermissions();

        if (p.isPresent() && !listPM.isEmpty()) {
            Profile currentProfile = p.get();
            Set<com.projet.housing.model.Permission> setP = new HashSet<>();
            
            listPM.forEach(x -> {
                if (x.getChecked()) {
                    Optional<com.projet.housing.model.Permission> pm = permissionService.getPermission(x.getId());
                    if (pm.isPresent()) {
                        setP.add(pm.get());
                    }
                }
            });
            currentProfile.setPermissions(setP);
            profileService.saveProfile(currentProfile);
        }
        return ResponseEntity.ok(h);
    }
}
