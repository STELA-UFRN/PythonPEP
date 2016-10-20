package br.ufrn.imd.fiware.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
 
import java.io.IOException;  

@ManagedBean(name = "userScimMBean")
@SessionScoped
public class UserScimMBean extends BaseBean implements Serializable {
	
	private String resultUsersList;  
	private String resultUserInfo; 
	private String accessToken;
	
	private String new_username;
	private String new_displayName;
	private String new_password;
	private String new_email;
	private String new_userId;
	
	private String edit_username;
	private String edit_displayName;
	private String edit_password;
	private String edit_email;
	private String edit_userId;
	
	private String delete_userId;
	private String find_userId; 
		
	public UserScimMBean() { 
		auth();
	}
	
	private void auth() {
		Client client = ClientBuilder.newClient();   
		Entity payload = Entity.json("{ \"auth\": { \"identity\": { \"methods\": [\"password\"], \"password\": { \"user\": {  \"name\": \"idm\", \"domain\": { \"id\": \"default\" }, \"password\": \"idm\"  } } } } }");

		Response response = client.target("http://192.168.99.100:5000/v3/auth/tokens")
		  .request(MediaType.TEXT_PLAIN_TYPE) 
		  .header("Content-Type", "application/json")
		  .post(payload);

		accessToken = response.getHeaders().get("X-Subject-Token").get(0).toString();  
	} 
	
	public void getUsers() throws IOException { 
		Client client = ClientBuilder.newClient();  
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/")
		  .request(MediaType.TEXT_PLAIN_TYPE) 
		  .header("X-Auth-token", accessToken)
		  .get();
		
		JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
		int total = (int) jsonObject.get("totalResults");
		JSONArray arr = jsonObject.getJSONArray("Resources"); 
		String user_data = "";
		for (int i = 0; i < arr.length(); i++) {
		    user_data += "id: " + arr.getJSONObject(i).getString("id") + "</br>";
		    user_data += "username: " + arr.getJSONObject(i).getString("userName") + "</br>"; 
		    user_data += "</br>";
		}
		
		setResultUsersList("Total: " + jsonObject.get("totalResults") + "</br>"
						 + user_data); 
	}

	public void createUser() { 
		Client client = ClientBuilder.newClient();
		Entity payload = Entity.json("{  \"userName\": \"" + new_username + "\",  "
				+ "\"displayName\": \"" + new_displayName + "\",  "
				+ "\"password\": \"" + new_password + "\", "
				+ " \"emails\": [    {      \"value\": \"" + new_email + "\"    }  ]}");
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/")
		  .request(MediaType.APPLICATION_JSON_TYPE)
		  .header("X-Auth-token", accessToken)
		  .header("Content-Type", "application/json")
		  .header("domain_id", "default")
		  .post(payload); 
	} 
	
	public void readUserInfo() { 
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + find_userId)
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("X-Auth-token", accessToken)
		  .get();
		JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

		setResultUserInfo("-- id: " + jsonObject.getString("id") + " <br>"
							+ "-- username: " + jsonObject.getString("userName"));
	}
	
	public void updateUserInfo() { 
		Client client = ClientBuilder.newClient();
		Entity payload = Entity.json("{  \"userName\": \"" + edit_username + "\",  "
				+ "\"displayName\": \"" + edit_displayName + "\",  "
				+ "\"password\": \"" + edit_password + "\", "
				+ " \"emails\": [    {      \"value\": \"" + edit_email + "\"    }  ]}");
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + edit_userId)
		  .request(MediaType.APPLICATION_JSON_TYPE)
		  .header("X-Auth-token", accessToken)
		  .put(payload); 
	}
	
	public void deleteUser() {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.99.100:5000/v3/OS-SCIM/v2/Users/" + delete_userId)
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("X-Auth-token", accessToken)
		  .delete(); 
	}
	
	public String getResultUsersList() {
		return resultUsersList;
	}

	public void setResultUsersList(String resultUsersList) {
		this.resultUsersList = resultUsersList;
	} 

	public String getResultUserInfo() {
		return resultUserInfo;
	}

	public void setResultUserInfo(String resultUserInfo) {
		this.resultUserInfo = resultUserInfo;
	} 

	public String getNew_username() {
		return new_username;
	}

	public void setNew_username(String username) {
		this.new_username = username;
	} 

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String password) {
		this.new_password = password;
	}

	public String getNew_email() {
		return new_email;
	}

	public void setNew_email(String email) {
		this.new_email = email;
	}

	public String getNew_displayName() {
		return new_displayName;
	}

	public void setNew_displayName(String displayName) {
		this.new_displayName = displayName;
	}
	
	public String getNew_userId() {
		return new_userId;
	}

	public void setNew_userId(String userId) {
		this.new_userId = userId;
	} 

	public String getEdit_username() {
		return edit_username;
	}

	public void setEdit_username(String edit_username) {
		this.edit_username = edit_username;
	}

	public String getEdit_displayName() {
		return edit_displayName;
	}

	public void setEdit_displayName(String edit_displayName) {
		this.edit_displayName = edit_displayName;
	}

	public String getEdit_email() {
		return edit_email;
	}

	public void setEdit_email(String edit_email) {
		this.edit_email = edit_email;
	}

	public String getEdit_password() {
		return edit_password;
	}

	public void setEdit_password(String edit_password) {
		this.edit_password = edit_password;
	}

	public String getEdit_userId() {
		return edit_userId;
	}

	public void setEdit_userId(String edit_userId) {
		this.edit_userId = edit_userId;
	}
	
	public String getDelete_userId() {
		return delete_userId;
	}

	public void setDelete_userId(String delete_userId) {
		this.delete_userId = delete_userId;
	}
	
	public String getFind_userId() {
		return find_userId;
	}

	public void setFind_userId(String find_userId) {
		this.find_userId = find_userId;
	}
}
