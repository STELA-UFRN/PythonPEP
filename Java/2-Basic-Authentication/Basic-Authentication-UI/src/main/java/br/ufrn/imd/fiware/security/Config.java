/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {
        // Application settings
	public static final String APPLICATION_ADDRESS = "http://localhost:8080";
        public static final String APPLICATION_UI = "/Basic-Authentication-UI";
        public static final String APPLICATION_REST = "/Basic-Authentication-REST";
        
        // API settings
        public static final String ACCESS_TOKEN_URL = "/oauth2/token";
        public static final String ACCESS_AUTH_URL = "/oauth2/authorize";
        public static final String CALLBACK = "/auth";

	// IDM settings
	public static final String IDM_ADDRESS = "http://10.7.52.86:3000";
	public static final String CLIENT_ID = "04700e04-20d5-45f8-8dbd-993ecad434f9";
	public static final String CLIENT_SECRET = "5039ce8b-e595-4426-98ed-ccf3299b261c";
	
	public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
}
