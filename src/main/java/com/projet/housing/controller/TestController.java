/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lerusse
 */
@RestController
@RequestMapping("/")
public class TestController {
    @RequestMapping("test")
    public ResponseEntity<?> getUser(){
        Map<String, Object> user = new HashMap<>();
        user.put("name", "toto");
        user.put("city", "togo");
        return ResponseEntity.ok(user);
    }
}
