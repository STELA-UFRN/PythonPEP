package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {

	public static final String PROXY_ADDRESS = "192.168.99.100:80";
	public static final String APPLICATION_ADDRESS = "192.168.99.101:8080";
	
//	//Real APP
//	public static final String IDM_ADDRESS = "http://10.7.31.29:8000";	
//	public static final String CLIENT_ID = "4cea9033583447459f9b319b661fba6f";
//	public static final String CLIENT_SECRET = "934ab80355744d6a9973310239746e12";

	//Docker APP
	public static final String IDM_ADDRESS = "http://192.168.99.100:8000";
	public static final String CLIENT_ID = "90cd61d192f34a97a48c2682f933497a";
	public static final String CLIENT_SECRET = "87df57f021f04c01bc93d2372c2d7901";
	
	public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
	
}
