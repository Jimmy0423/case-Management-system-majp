package se.majp.cms.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.majp.cms.web.auth.AuthProvider;

@Path("logout")
@Consumes(MediaType.APPLICATION_JSON)
public class LogoutWebService
{
	@POST
	public Response userLogOut(final String token)
	{
		AuthProvider provider = new AuthProvider();
		provider.removeToken(token);
		
		return Response.noContent().build();
	}
}
