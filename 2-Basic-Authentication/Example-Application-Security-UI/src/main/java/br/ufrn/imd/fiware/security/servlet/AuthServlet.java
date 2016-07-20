package br.ufrn.imd.fiware.security.servlet;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String CLIENT_SECRET = "934ab80355744d6a9973310239746e12";
	private static final String CLIENT_ID = "4cea9033583447459f9b319b661fba6f";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/auth");
		
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		try {
			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
			
			String code = oar.getCode();
			System.out.println("Code: " + code);
			
			OAuthClientRequest tokenRequest = OAuthClientRequest
	                .tokenLocation("http://10.7.31.29:8000/oauth2/token")
	                .setGrantType(GrantType.AUTHORIZATION_CODE)
	                .setClientId(CLIENT_ID)
	                .setClientSecret(CLIENT_SECRET)
	                .setRedirectURI("http://localhost:8080/Example-Application-Security-UI/")
	                .setCode(code)
	                .buildQueryMessage();
			
//			tokenRequest.setHeader("Authorization", "Basic NGNlYTkwMzM1ODM0NDc0NTlmOWIzMTliNjYxZmJhNmY6OTM0YWI4MDM1NTc0NGQ2YTk5NzMzMTAy Mzk3NDZlMTI=");
//			tokenRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
			
			System.out.println("Headers");
			System.out.println(tokenRequest.getHeaders());
			
			System.out.println("Body");
			System.out.println(tokenRequest.getBody());
			
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			
			OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(tokenRequest);
			
			System.out.println(oAuthResponse.getBody());
			System.out.println(oAuthResponse);
			
			String accessToken = oAuthResponse.getAccessToken();
	        Long expiresIn = oAuthResponse.getExpiresIn();
	      
	        System.out.println("Token: " + accessToken);
	        System.out.println("Expires In: " + expiresIn);

	        //Add token on cookies or session
			
			resp.sendRedirect("http://www.google.com");
		} catch (OAuthProblemException e) {
			// TODO Auto-generated catch block
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
