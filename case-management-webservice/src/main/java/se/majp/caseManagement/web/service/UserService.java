package se.majp.caseManagement.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.majp.caseManagement.model.User;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserService
{
	@Autowired
	se.majp.caseManagement.service.UserService userService;
	
	@GET
	public Response addUser()
	{		
		User user = userService.addUser(new User("userId", "email", "pass", "Marcus", "Svensson"));
		return Response.ok(user.getEmail()).build();
	}
}
