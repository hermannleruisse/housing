/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.db;

import com.projet.housing.model.Menu;
import com.projet.housing.model.Permission;
import com.projet.housing.model.Profile;
import com.projet.housing.model.User;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author lerusse
 */
@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PermissionRepository permissionRepository;
    private MenuRepository menuRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository, 
            PermissionRepository permissionRepository, MenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.permissionRepository = permissionRepository;
        this.menuRepository = menuRepository;
    }
    
    @Override
    public void run(String... args) {
        //delete all
        // this.permissionRepository.deleteAll();
        // this.menuRepository.deleteAll();
        // this.userRepository.deleteAll();
        // this.profileRepository.deleteAll();
        
        Profile p1 = new Profile("ADMIN", "ADMIN", "Administrateur du system");
        Profile p2 = new Profile("MANAGER", "MANAGER", "Manager du system");
        List<Profile> profiles = Arrays.asList(p1, p2);
        this.profileRepository.saveAll(profiles);
        
        Menu m1 = new Menu("Habilitation");
        Menu m2 = new Menu("Utilisateur");
        Menu m3 = new Menu("Profile");
        Menu m4 = new Menu("Ministére");
        Menu m5 = new Menu("Membre");
        List<Menu> menus = Arrays.asList(m1, m2, m3, m4, m5);
        this.menuRepository.saveAll(menus);
        //habilitation
        Permission pm1 = new Permission("PM_EDI_H", "Modifier", m1);
        Permission pm2 = new Permission("PM_ADD_H", "Ajouter", m1);
        Permission pm3 = new Permission("PM_DEL_H", "Supprimer", m1);
        //utilisateur
        Permission pm4 = new Permission("PM_EDI_U", "Modifier", m2);
        Permission pm5 = new Permission("PM_ADD_U", "Ajouter", m2);
        Permission pm6 = new Permission("PM_DEL_U", "Supprimer", m2);
        //profile
        Permission pm7 = new Permission("PM_EDI_P", "Modifier", m3);
        Permission pm8 = new Permission("PM_ADD_P", "Ajouter", m3);
        Permission pm9 = new Permission("PM_DEL_P", "Supprimer", m3);
        //Ministère
        Permission pm10 = new Permission("PM_EDI_MI", "Modifier", m4);
        Permission pm11 = new Permission("PM_ADD_MI", "Ajouter", m4);
        Permission pm12 = new Permission("PM_DEL_MI", "Supprimer", m4);
        //Membre
        Permission pm13 = new Permission("PM_EDI_ME", "Modifier", m5);
        Permission pm14 = new Permission("PM_ADD_ME", "Ajouter", m5);
        Permission pm15 = new Permission("PM_DEL_ME", "Supprimer", m5);
        Permission pm16 = new Permission("PM_DET_ME", "Détails", m5);
        
        List<Permission> permissions = Arrays.asList(pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8, pm9, pm10, pm11, pm12,
        pm13, pm14, pm15, pm16);

        this.permissionRepository.saveAll(permissions);

        User admin = new User("admin", passwordEncoder.encode("admin123"), p1);
        User manager = new User("manager", passwordEncoder.encode("manager123"), p2);
        
        List<User> users = Arrays.asList(admin, manager);
        this.userRepository.saveAll(users);
    }
}
