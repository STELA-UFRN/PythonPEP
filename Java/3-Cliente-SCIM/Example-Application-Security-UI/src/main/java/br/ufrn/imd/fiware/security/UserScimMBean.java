package br.ufrn.imd.fiware.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


import org.json.JSONObject; 

@ManagedBean(name = "userScimMBean")
@SessionScoped
public class UserScimMBean extends BaseBean implements Serializable {
	
	private String resultUsersList; 
	private String resultCreateUser; 
	private String resultUserInfo; 
	
	public UserScimMBean() {
		
	}
	
	public void getUsers() throws IOException {
		String accessToken = getAccessToken(); 
		
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/")
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("X-Auth-token", accessToken)
		  .get();
		
		setResultUsersList("-- status: " + response.getStatus() + " <br>" 
				+ "-- headers: " + response.getHeaders() + " <br>"
				+ "-- body: " + response.readEntity(String.class) + " <br>"
				+ "-- token: " + accessToken);
	}

	public void createUser() {
		String accessToken = getAccessToken();
		
		Client client = ClientBuilder.newClient();
		Entity payload = Entity.json("{  'userName': 'test_username',  'displayName': 'Test',  'password': 'passw0rd',  'emails': [    {      'value': 'test@mailhost.com'    }  ]}");
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/")
		  .request(MediaType.APPLICATION_JSON_TYPE)
		  .header("X-Auth-token", accessToken)
		  .post(payload);

		setResultCreateUser("-- status: " + response.getStatus() + " <br>" 
				+ "-- headers: " + response.getHeaders() + " <br>"
				+ "-- body: " + response.readEntity(String.class) + " <br>"
				+ "-- token: " + accessToken);
	}

	private String getAccessToken() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = httpServletRequest.getSession();
		String accessToken = (String) session.getAttribute("access_token");
		return accessToken;
	}
	
	public void readUserInfo(String user_id) {
		String accessToken = getAccessToken();

		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + user_id)
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("X-Auth-token", "token")
		  .get();

		setResultUserInfo("-- status: " + response.getStatus() + " <br>" 
				+ "-- headers: " + response.getHeaders() + " <br>"
				+ "-- body: " + response.readEntity(String.class) + " <br>"
				+ "-- token: " + accessToken);
	}
	
	public void updateUserInfo(String user_id) {
		String accessToken = getAccessToken();

		Client client = ClientBuilder.newClient();
		Entity payload = Entity.json("{  'userName': 'test_username',  'displayName': 'Test',  'password': 'passw0rd_new',  'emails': [    {      'value': 'test@mailhost.com'    }  ]}");
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + user_id)
		  .request(MediaType.APPLICATION_JSON_TYPE)
		  .header("X-Auth-token", accessToken)
		  .put(payload);

		System.out.println("status: " + response.getStatus());
		System.out.println("headers: " + response.getHeaders());
		System.out.println("body:" + response.readEntity(String.class));
	}
	
	public void deleteUser(String user_id) {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + user_id)
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("X-Auth-token", "token")
		  .delete();

		System.out.println("status: " + response.getStatus());
		System.out.println("headers: " + response.getHeaders());
		System.out.println("body:" + response.readEntity(String.class));
	}
	
	public String getResultUsersList() {
		return resultUsersList;
	}

	public void setResultUsersList(String resultUsersList) {
		this.resultUsersList = resultUsersList;
	}

	public String getResultCreateUser() {
		return resultCreateUser;
	}

	public void setResultCreateUser(String resultCreateUser) {
		this.resultCreateUser = resultCreateUser;
	}

	public String getResultUserInfo() {
		return resultUserInfo;
	}

	public void setResultUserInfo(String resultUserInfo) {
		this.resultUserInfo = resultUserInfo;
	}
}
