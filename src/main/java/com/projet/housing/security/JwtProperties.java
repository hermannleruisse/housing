/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.security;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author lerusse
 */
public class JwtProperties {
    @Value("${spring.custom.secret:hermann}")
    public static String SECRET;
    @Value("${spring.custom.expiration:864000000}")
    public static int EXPIRATION_TIME; //10 jours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
