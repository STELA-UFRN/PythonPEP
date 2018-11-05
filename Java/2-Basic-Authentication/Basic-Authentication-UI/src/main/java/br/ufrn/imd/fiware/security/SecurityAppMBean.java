package br.ufrn.imd.fiware.security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static final String URL_SERVICE_1 = Config.APPLICATION_ADDRESS + Config.APPLICATION_REST + "/securityapp/service1";
	private static final String URL_SERVICE_2 = Config.APPLICATION_ADDRESS + Config.APPLICATION_REST + "/securityapp/service2";

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
				.authorizationLocation(Config.IDM_ADDRESS + Config.ACCESS_AUTH_URL)
				.setParameter("response_type", "code")
                                .setParameter("state", "xyz")
				.setClientId(Config.CLIENT_ID)
				.setRedirectURI(Config.APPLICATION_ADDRESS + Config.APPLICATION_UI + Config.CALLBACK)
				.buildQueryMessage();
		
		httpServletResponse.sendRedirect(codeRequest.getLocationUri());
	}
        
        public String getAccessToken () {
            HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = httpServletRequest.getSession();
            String accessToken = (String) session.getAttribute("access_token");
            
            if (accessToken == null) {
                return "";
            } else {
                return accessToken;
            }
        }
        
        public boolean validateUser () {
            try {
                callWebservice(Config.IDM_ADDRESS + "/user?access_token=" + getAccessToken());
                return true;
            } catch (UnauthorizedException e) {
                return false;
            }
        } 
	
	public void requestUserInfo() {
		
		String accessToken = getAccessToken();
                try {
                    String strJson = callWebservice(Config.IDM_ADDRESS + "/user?access_token=" + accessToken);
                    JSONObject jsonObject = new JSONObject(strJson);
                    
                    resultUserInfo = "Username: " + jsonObject.getString("username") + "\n";
                    resultUserInfo += "Email: " + jsonObject.getString("email") + "\n";
                    resultUserInfo += "Roles: ";
                    
                    JSONArray roles = jsonObject.getJSONArray("roles");
                    Iterator<Object> it = roles.iterator();
                    
                    while (it.hasNext()) {
                        JSONObject role = (JSONObject) it.next();
                        resultUserInfo += role.getString("name");
                        
                        if (it.hasNext()) {
                            resultUserInfo += ", ";
                        }
                    }
                    
                    resultUserInfo += "\n";
                } catch (UnauthorizedException e) {
                    resultUserInfo = "Operation needs authentication!";
                }
	}

	public void helloWorld() {	
            if (validateUser()) {
                try {
                    String strJson;
                    strJson = callWebservice(URL_SERVICE_1);
                    JSONObject jsonObject = new JSONObject(strJson);
                    resultHelloWorld = jsonObject.getString("result");
                } catch (UnauthorizedException ex) {
                    Logger.getLogger(SecurityAppMBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                resultHelloWorld = "Operation needs authentication!";
            }
		
	}

	public void greeting() {
            if (validateUser()) {
                if (!username.equals("")) {
                    try {
                        String strJson = callWebservice(URL_SERVICE_1 + "/" + username);
                        JSONObject jsonObject = new JSONObject(strJson);
                        resultGreeting = jsonObject.getString("result");
                        username = "";
                    } catch (UnauthorizedException ex) {
                        Logger.getLogger(SecurityAppMBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
		} else {
			resultGreeting = "Fill the username field first!";
		}
            } else {
                resultGreeting = "Operation needs authentication!";
            }
	}

	public void listNames() {
            if (validateUser()) {
                try {
                    String strJson = callWebservice(URL_SERVICE_2);
                    JSONObject jsonObject = new JSONObject(strJson);
                    JSONArray jsonArrayNames = jsonObject.getJSONArray("names");
                    resultListNames = jsonArrayNames.toString();
                } catch (UnauthorizedException ex) {
                    Logger.getLogger(SecurityAppMBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                resultListNames = "Operation needs authentication!";
            }
	}

	public void addName() {
            if (validateUser()) {
                if (!name.equals("")) {
                    try {
                        callWebservice(URL_SERVICE_2 + "/" + name);
                        resultAddName = "Name added on list!";
                        name = "";
                    } catch (UnauthorizedException ex) {
                        Logger.getLogger(SecurityAppMBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
		} else {
			resultAddName = "Fill the name field first!";
		}
            } else {
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
		HttpURLConnection connection = null;
		String result = "";
                
                try {
			URL url = new URL(urlWebservice);
			connection = (HttpURLConnection) url.openConnection();
                        
			InputStream inputStream = connection.getInputStream();
			result = getStringFromInputStream(inputStream);
                        System.out.println(result);

		} catch (IOException e) {
                    e.printStackTrace();
                    try {
                        int responseCode = connection.getResponseCode ();
          
                        if (responseCode == 401) {
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
        

//	private String callWebservice(String urlWebservice) {
//		HttpURLConnection connection = null;
//		try {
//			URL url = new URL(urlWebservice);
//			connection = (HttpURLConnection) url.openConnection();
//
//			InputStream inputStream = connection.getInputStream();
//			return getStringFromInputStream(inputStream);
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//			connection.disconnect();
//		}
//	}

}
