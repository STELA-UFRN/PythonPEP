package br.ufrn.imd.securityapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/service1")
public class Service1 {
	
	@GET
	@Produces("application/json")
	public Response helloWorld() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "Hello, World!");

		String result = jsonObject.toString();
		System.out.println(result);
		return Response.status(200).entity(result).build();
	}
	
	@Path("{username}")
	@GET
	@Produces("application/json")
	public Response greeting(@PathParam("username") String username) throws JSONException {
		String resultString = "Hello, " + username + "!";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", resultString);			

		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

}
