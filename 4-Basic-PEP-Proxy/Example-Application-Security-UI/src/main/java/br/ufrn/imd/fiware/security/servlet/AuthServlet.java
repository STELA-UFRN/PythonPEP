package br.ufrn.imd.fiware.security.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import br.ufrn.imd.fiware.security.Config;

public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
											throws ServletException, IOException {
		try {
			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
			String code = oar.getCode();

			OAuthClientRequest tokenRequest = OAuthClientRequest
					.tokenLocation(Config.IDM_ADDRESS + "/oauth2/token")
					.setGrantType(GrantType.AUTHORIZATION_CODE).setCode(code)
					.setRedirectURI("http://" + Config.APPLICATION_ADDRESS + "/Example-Application-Security-UI/auth")
					.buildBodyMessage();

			tokenRequest.setHeader("Authorization", "Basic " + Config.BASE_64_ENCODED_STRING);
			tokenRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(tokenRequest);

			String accessToken = oAuthResponse.getAccessToken();
			Long expiresIn = oAuthResponse.getExpiresIn();

			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("access_token", accessToken);
			httpSession.setAttribute("expires_in", expiresIn);

			System.out.println("GENERATED TOKEN: " + accessToken);
			
			resp.sendRedirect("http://" + Config.APPLICATION_ADDRESS + "/Example-Application-Security-UI/");
		} catch (OAuthProblemException e) {
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		} catch (OAuthSystemException e) {
			System.err.println("Error on authentication. Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
