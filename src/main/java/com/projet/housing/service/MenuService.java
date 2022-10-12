/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.service;

import com.projet.housing.db.MenuRepository;
import com.projet.housing.model.Menu;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lerusse
 */
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Optional<Menu> getMenu(final String id) {
        return menuRepository.findById(id);
    }

    public Iterable<Menu> getMenus() {
        return menuRepository.findAll();
    }
}
