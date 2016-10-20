package br.ufrn.imd.fiware.security;

import org.apache.commons.codec.binary.Base64;

public class Config {

	public static final String PROXY_ADDRESS = "192.168.99.100:80";
	public static final String APPLICATION_ADDRESS = "127.0.0.1:5000";
	
//	//Real APP
//	public static final String IDM_ADDRESS = "http://10.7.31.29:8000";	
//	public static final String CLIENT_ID = "4cea9033583447459f9b319b661fba6f";
//	public static final String CLIENT_SECRET = "934ab80355744d6a9973310239746e12";

	//Docker APP
	public static final String IDM_ADDRESS = "http://192.168.99.100:8000";
	public static final String CLIENT_ID = "88b383b409e74441b9d8f02b6afa0b2c";
	public static final String CLIENT_SECRET = "0200572e51744454acd35e71bd9473ac";
	
	public static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
	
}
