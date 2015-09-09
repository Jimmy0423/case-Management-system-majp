package se.majp.caseManagement.web.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.UserService;

@Path("login")
public class LoginService
{
	@Autowired
	private UserService userService;
	
	@POST
	public Response login(final User user)
	{
		final User userFromDb = userService.findByEmail(user.getEmail());
		
		if(user.getPassword().equals(userFromDb.getPassword()))
		{
			AuthProvider provider = new AuthProvider();
			final String token = provider.addToken(userFromDb.getId());
			
			return Response.accepted().header("token", token).build();
		}
		
		throw new AuthorizationException("Wrong username or password");
	}
}
