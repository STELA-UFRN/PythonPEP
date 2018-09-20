package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {

     // Application settings
	public static final String APPLICATION_ADDRESS = "http://localhost:8080";
        public static final String APPLICATION_UI = "/Basic-PEP-Authentication-UI";
        public static final String APPLICATION_REST = "/Basic-PEP-Authentication-REST";
        
        // API settings
        public static final String ACCESS_TOKEN_URL = "/oauth2/token";
        public static final String ACCESS_AUTH_URL = "/oauth2/authorize";
        public static final String CALLBACK = "/auth";

	// IDM and PEP settings
	public static final String IDM_ADDRESS = "http://10.7.52.86:3000";
        public static final String PROXY_ADDRESS = "http://10.7.52.86:8000";
	public static final String CLIENT_ID = "679c956d-89de-4cfd-9c93-f460c59cb793";
	public static final String CLIENT_SECRET = "0bdb4405-31f8-4361-9b79-0acbb2b1effb";

    
    public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
	
}
