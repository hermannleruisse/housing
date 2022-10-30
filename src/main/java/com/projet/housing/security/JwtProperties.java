/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.housing.security;

/**
 *
 * @author lerusse
 */
public class JwtProperties {
    public static final String SECRET = "hermann";
    public static final int EXPIRATION_TIME = 900000; //10 jours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
