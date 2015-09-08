package se.majp.caseManagement.web.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.service.UserService;

@Path("users")
public class StoryService
{
	@Autowired
	UserService userService;

	@GET
	public Response hello()
	{
		userService.findAllStories("");
		return Response.status(200).entity("hello").build();
	}

}
