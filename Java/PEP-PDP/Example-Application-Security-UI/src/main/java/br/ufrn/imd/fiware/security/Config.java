package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {

	public static final String PROXY_ADDRESS = "10.7.52.86:80";
	public static final String APPLICATION_ADDRESS = "localhost:8080";
	
//	//Real APP
//	public static final String IDM_ADDRESS = "http://10.7.31.29:8000";	
//	public static final String CLIENT_ID = "4cea9033583447459f9b319b661fba6f";
//	public static final String CLIENT_SECRET = "934ab80355744d6a9973310239746e12";

	//Docker APP
	public static final String IDM_ADDRESS = "http://10.7.52.86:3000";
	public static final String CLIENT_ID = "2d10f3b7-f7a3-4e66-8005-c974994d2ecb";
	public static final String CLIENT_SECRET = "b3bfb5fb-fba6-4192-b9cd-16b1c187434a";
	
	public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
	
}
