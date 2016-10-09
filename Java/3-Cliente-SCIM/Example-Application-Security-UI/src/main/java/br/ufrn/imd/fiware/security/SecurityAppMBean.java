package br.ufrn.imd.fiware.security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name = "securityAppMBean")
@SessionScoped
public class SecurityAppMBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String CLIENT_ID = "88b383b409e74441b9d8f02b6afa0b2c";

	private static final String URL_SERVICE_1 = "http://localhost:8080/Example-Application-Security-REST/securityapp/service1";
	private static final String URL_SERVICE_2 = "http://localhost:8080/Example-Application-Security-REST/securityapp/service2";

	private String username;
	private String name;
	private String accessToken;

	private String resultUserInfo;
	private String resultHelloWorld, resultGreeting;
	private String resultListNames, resultAddName;

	public SecurityAppMBean() {
		this.username = "";
		this.name = "";
	}
	
	public void authenticateUser() throws OAuthSystemException, IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		OAuthClientRequest codeRequest = OAuthClientRequest
				.authorizationLocation("http://192.168.99.100:8000/oauth2/authorize")
				.setParameter("response_type", "code")
				.setClientId(CLIENT_ID)
				.setRedirectURI("http://localhost:8080/Example-Application-Security-UI/auth")
				.buildQueryMessage();
		
		httpServletResponse.sendRedirect(codeRequest.getLocationUri());
	}
	
	public void requestUserInfo() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = httpServletRequest.getSession();
		accessToken = (String) session.getAttribute("access_token");
		
		String strJson = callWebservice("http://192.168.99.100:8000/user?access_token=" + accessToken);
		JSONObject jsonObject = new JSONObject(strJson);
		resultUserInfo = jsonObject.toString();
	}

	public void helloWorld() {		
		String strJson = callWebservice(URL_SERVICE_1);
		JSONObject jsonObject = new JSONObject(strJson);
		resultHelloWorld = jsonObject.getString("result");
	}

	public void greeting() {
		if (!username.equals("")) {
			String strJson = callWebservice(URL_SERVICE_1 + "/" + username);
			JSONObject jsonObject = new JSONObject(strJson);
			resultGreeting = jsonObject.getString("result");
			username = "";
		} else {
			resultGreeting = "Fill the username field first!";
		}
	}

	public void listNames() {
		String strJson = callWebservice(URL_SERVICE_2);
		JSONObject jsonObject = new JSONObject(strJson);
		JSONArray jsonArrayNames = jsonObject.getJSONArray("names");
		resultListNames = jsonArrayNames.toString();
	}

	public void addName() {
		if (!name.equals("")) {
			callWebservice(URL_SERVICE_2 + "/" + name);
			resultAddName = "Name added on list!";
			name = "";
		} else {
			resultAddName = "Fill the name field first!";
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getResultUserInfo() {
		return resultUserInfo;
	}

	public void setResultUserInfo(String resultUserInfo) {
		this.resultUserInfo = resultUserInfo;
	}

	public String getResultHelloWorld() {
		return resultHelloWorld;
	}

	public void setResultHelloWorld(String resultHelloWorld) {
		this.resultHelloWorld = resultHelloWorld;
	}

	public String getResultGreeting() {
		return resultGreeting;
	}

	public void setResultGreeting(String resultGreeting) {
		this.resultGreeting = resultGreeting;
	}

	public String getResultListNames() {
		return resultListNames;
	}

	public void setResultListNames(String resultListNames) {
		this.resultListNames = resultListNames;
	}

	public String getResultAddName() {
		return resultAddName;
	}

	public void setResultAddName(String resultAddName) {
		this.resultAddName = resultAddName;
	}
	
	public void goToUserGUI() throws IOException {
		GoPage("user_gui.xhtml");
	}
	
	public void goToHome() throws IOException {
		GoPage("index.xhtml");
	}

	private void GoPage(String link) throws IOException { 
		FacesContext.getCurrentInstance().getExternalContext().redirect(link);
	}
}
