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

import org.json.JSONObject; 

@ManagedBean(name = "userScimMBean")
@SessionScoped
public class UserScimMBean extends BaseBean implements Serializable {
	
	private String resultUsersList; 
	
	public UserScimMBean() {
		
	}
	
	public void getUsers() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = httpServletRequest.getSession();
		String accessToken = (String) session.getAttribute("access_token"); 
		
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

	public String getResultUsersList() {
		return resultUsersList;
	}

	public void setResultUsersList(String resultUsersList) {
		this.resultUsersList = resultUsersList;
	}
}
