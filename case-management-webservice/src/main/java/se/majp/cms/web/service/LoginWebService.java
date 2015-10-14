package se.majp.cms.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.cms.model.Credential;
import se.majp.cms.model.User;
import se.majp.cms.service.UserService;
import se.majp.cms.web.auth.AuthProvider;

@Path("login")
@Consumes(MediaType.APPLICATION_JSON)
public class LoginWebService
{
	@Autowired
	private UserService userService;

	@POST
	public Response authenticateUser(Credential credential)
	{
		AuthProvider provider = new AuthProvider();

		User authenticatedUser = userService.authenticate(credential);
		return Response.ok().header(HttpHeaders.AUTHORIZATION, provider.generateToken(authenticatedUser.getUserId())).build();
	}
}
