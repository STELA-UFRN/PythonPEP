package br.ufrn.imd.fiware.security.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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

	private static final String CLIENT_SECRET = "b3bfb5fb-fba6-4192-b9cd-16b1c187434a";
	private static final String CLIENT_ID = "2d10f3b7-f7a3-4e66-8005-c974994d2ecb";
	private static final String BASE_64_ENCODED_STRING = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
											throws ServletException, IOException {
		try {
			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
			String code = oar.getCode();

			OAuthClientRequest tokenRequest = OAuthClientRequest
					.tokenLocation("http://10.7.52.86:3000/oauth2/token")
					.setGrantType(GrantType.AUTHORIZATION_CODE).setCode(code)
					.setRedirectURI("http://localhost:8080/Example-Application-Security-UI/auth")
					.buildBodyMessage();

			tokenRequest.setHeader("Authorization", "Basic " + BASE_64_ENCODED_STRING);
			tokenRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(tokenRequest);

			String accessToken = oAuthResponse.getAccessToken();
			Long expiresIn = oAuthResponse.getExpiresIn();

			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("access_token", accessToken);
			httpSession.setAttribute("expires_in", expiresIn);

			resp.sendRedirect("http://localhost:8080/Example-Application-Security-UI/");
		} catch (OAuthProblemException e) {
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		} catch (OAuthSystemException e) {
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
