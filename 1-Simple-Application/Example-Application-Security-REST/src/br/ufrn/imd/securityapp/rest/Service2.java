package br.ufrn.imd.securityapp.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/service2")
public class Service2 {

	private static List<String> names = new ArrayList<>();
	
	@GET
	@Produces("application/json")
	public Response listNames() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("names", names);

		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}
	
	@Path("{name}")
	@GET
	@Produces("application/json")
	public Response addName(@PathParam("name") String name) throws JSONException {
		names.add(name);

		return Response.status(200).build();
	}

}
