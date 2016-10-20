package br.ufrn.imd.fiware.security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

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
public class SecurityAppMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String URL_SERVICE_1 = "http://" + Config.PROXY_ADDRESS + "/service1";
	private static final String URL_SERVICE_2 = "http://" + Config.PROXY_ADDRESS + "/service2";

	private String username;
	private String name;

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
				.authorizationLocation(Config.IDM_ADDRESS + "/oauth2/authorize")
				.setParameter("response_type", "code")
				.setClientId(Config.CLIENT_ID)
				.setRedirectURI("http://" + Config.APPLICATION_ADDRESS + "/Example-Application-Security-UI/auth")
				.buildQueryMessage();
		
		httpServletResponse.sendRedirect(codeRequest.getLocationUri());
	}
	
	private String getAccessToken() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = httpServletRequest.getSession();
		String accessToken = (String) session.getAttribute("access_token");
		return accessToken != null ? accessToken : "";
	}
	
	public void requestUserInfo() {
		try {
			String accessToken = getAccessToken();
			String strJson = callWebservice(Config.IDM_ADDRESS + "/user?access_token=" + accessToken);
			JSONObject jsonObject = new JSONObject(strJson);
			
			resultUserInfo = "";
			resultUserInfo += "Display Name: " + jsonObject.getString("displayName") + "\n";
			resultUserInfo += "E-mail: " + jsonObject.getString("email") + "\n";
			
			String strRoles = "";
			JSONArray roles = jsonObject.getJSONArray("roles");
			Iterator<Object> it = roles.iterator();
			while(it.hasNext()) {
				JSONObject role = (JSONObject) it.next();
				strRoles += role.getString("name");
				if(it.hasNext()) {
					strRoles += ", ";
				}
			}
			resultUserInfo += "Roles: " + strRoles + "\n";
		} catch (UnauthorizedException e) {
			resultUserInfo = "Operation needs authentication!";
		}
	}

	public void helloWorld() {		
		try {
			String accessToken = getAccessToken();
			String strJson;
			strJson = callWebservice(URL_SERVICE_1, accessToken);
			//JSONObject jsonObject = new JSONObject(strJson);
			resultHelloWorld = strJson; //jsonObject.getString("result");
		} catch (UnauthorizedException e) {
			resultHelloWorld = "Operation needs authentication!";
		}
	}

	public void greeting() {
		try {
			if (!username.equals("")) {
				String accessToken = getAccessToken();
				String strJson = callWebservice(URL_SERVICE_1 + "/" + username, accessToken);
				JSONObject jsonObject = new JSONObject(strJson);
				resultGreeting = jsonObject.getString("result");
				username = "";
			} else {
				resultGreeting = "Fill the username field first!";
			}
		} catch (UnauthorizedException e) {
			resultGreeting = "Operation needs authentication!";
		}
	}

	public void listNames() {
		try {
			String accessToken = getAccessToken();
			String strJstrJson = callWebservice(URL_SERVICE_2, accessToken);
			JSONObject jsonObject = new JSONObject(strJstrJson);
			JSONArray jsonArrayNames = jsonObject.getJSONArray("names");
			resultListNames = jsonArrayNames.toString();
		} catch (UnauthorizedException e) {
			resultListNames = "Operation needs authentication!";
		}
	}

	public void addName() {
		try {
			if (!name.equals("")) {
				String accessToken = getAccessToken();
				callWebservice(URL_SERVICE_2 + "/" + name, accessToken);
				resultAddName = "Name added on list!";
				name = "";
			} else {
				resultAddName = "Fill the name field first!";
			}
		} catch (UnauthorizedException e) {
			resultAddName = "Operation needs authentication!";
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

	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	private String callWebservice(String urlWebservice) throws UnauthorizedException { 
		return callWebservice(urlWebservice, "");
	}
	
	private String callWebservice(String urlWebservice, String accessToken) throws UnauthorizedException { 
		String result = "";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlWebservice);
			connection = (HttpURLConnection) url.openConnection();
			
			if(!accessToken.equals("")) {
				connection.setRequestProperty("X-Auth-Token", accessToken);
			}
			
			InputStream inputStream = connection.getInputStream();	
			result = getStringFromInputStream(inputStream);
		} catch (IOException e) {
			try {
				int responseCode = connection.getResponseCode();
				if(responseCode == 401) {
					throw new UnauthorizedException();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			connection.disconnect();
		}
		return result;
	}

}
