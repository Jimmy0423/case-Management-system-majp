package se.majp.cms.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.majp.cms.model.Credential;

@Path("authentication")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationWebService
{
	public Response authenticateUser(Credential credential)
	{
		
		
		
		return Response.ok().build();
	}
}
