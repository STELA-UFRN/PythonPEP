package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {
        
        // Application settings
	public static final String APPLICATION_ADDRESS = "http://localhost:8080";
        public static final String APPLICATION_UI = "/Basic-Authorization-UI";
        public static final String APPLICATION_REST = "/Basic-Authorization-REST";
        
        // API settings
        public static final String ACCESS_TOKEN_URL = "/oauth2/token";
        public static final String ACCESS_AUTH_URL = "/oauth2/authorize";
        public static final String CALLBACK = "/auth";

	// IDM and PEP settings
//	public static final String IDM_ADDRESS = "http://10.7.52.86:3000";
        public static final String IDM_ADDRESS = "https://keyrock.smcloud.imd.ufrn.br";
        public static final String PROXY_ADDRESS = "http://10.7.52.86:80";
	public static final String CLIENT_ID = "bc3f91f1-2214-41b5-830c-1ddef835032f";
	public static final String CLIENT_SECRET = "399c1f3d-d56a-4db3-925f-73d50a06aa28"; 

//        public static final String CLIENT_ID = "17428933-bd2e-4e86-864f-95380f8c3c75";
//	public static final String CLIENT_SECRET = "100221cd-dc45-44aa-9226-542e28ed0408";

        
        
	public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
	
}
