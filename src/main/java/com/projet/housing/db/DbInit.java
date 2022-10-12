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
        this.permissionRepository.deleteAll();
        this.menuRepository.deleteAll();
        this.userRepository.deleteAll();
        this.profileRepository.deleteAll();
        
        Profile p1 = new Profile("ADMIN", "ADMIN", "Administrateur du system");
        Profile p2 = new Profile("MANAGER", "MANAGER", "Manager du system");
        List<Profile> profiles = Arrays.asList(p1, p2);
        this.profileRepository.saveAll(profiles);
        
        Menu m1 = new Menu("Habilitation");
        Menu m2 = new Menu("Liste des utilisateurs");
        List<Menu> menus = Arrays.asList(m1, m2);
        this.menuRepository.saveAll(menus);
        
        Permission pm1 = new Permission("ACC_LIRE_H", "Lire", m1);
        Permission pm2 = new Permission("ACC_EDIT_H", "Modifier", m1);
        List<Permission> permissions = Arrays.asList(pm1, pm2);
        this.permissionRepository.saveAll(permissions);
        
        User admin = new User("admin", passwordEncoder.encode("admin123"), p1);
        User manager = new User("manager", passwordEncoder.encode("manager123"), p2);
//        User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN", "ACCESS_1, ACCESS_2");
//        User manager = new User("manager", passwordEncoder.encode("manager123"), "MANAGER", "ACCESS_1");
        
        List<User> users = Arrays.asList(admin, manager);
        this.userRepository.saveAll(users);
    }
}
