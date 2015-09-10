package se.majp.caseManagement.web.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.service.UserService;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Scope("request")
public final class UserWebService
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private StoryService storyService;
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	public Response addUser(final User user)
	{
		final User userFromDb = userService.addOrUpdateUser(user);
		final URI location = uriInfo.getAbsolutePathBuilder().path(userFromDb.getUserId()).build();
		
		return Response.created(location).build();
	}
	
	@GET
	@Path("{userId}")
	public Response getUserByUserId(@PathParam("userId") final String userId)
	{
		User user = userService.findByUserId(userId);
		return Response.ok(user).build();
	}
	
	@GET
	@Path("{userId}/projects")
	public Response getAllProjectsForUser(@PathParam("userId") final String userId)
	{
		List<Project> projects = projectService.findAllProjectsByUser(userId);
		GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projects){};
		
		return Response.ok(entity).build();
	}
	
	@GET
	@Path("{userId}/stories")
	public Response getAllStoriesForUser(@PathParam("userId") final String userId)
	{
		List<Story> stories = storyService.findAllStoriesAssignedToUser(userId);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
		return Response.ok(entity).build();
	}
	
	@PUT
	public Response updateUser(User user)
	{
		userService.addOrUpdateUser(user);
		return Response.ok().build();
	}
	
	@PUT
	@Path("{userId}/stories")
	public Response addStoryToUser(@PathParam("userId") final String userId, Story story)
	{
		storyService.addStoryToUser(userId, story);
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{userId}")
	public Response removeUser(@PathParam("userId") final String userId)
	{
		userService.removeUser(userId);
		return Response.noContent().build();
	}
}
