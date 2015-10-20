package se.majp.cms.web.service;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import se.majp.cms.model.Story;
import se.majp.cms.model.User;
import se.majp.cms.service.StoryService;
import se.majp.cms.service.UserService;
import se.majp.cms.web.auth.SecureUsers;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Scope("request")
public final class UserWebService
{
	@Autowired
	private UserService userService;

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
	public Response getUserByEmailOrFirstNameOrLastName(@QueryParam("searchTerm") final String value)
	{
		List<User> users = userService.searchByNameOrEmail(value);
		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users)
		{
		};

		return Response.ok(entity).build();
	}

	@GET
	@Path("{userId}")
	@SecureUsers
	public Response getUserByUserId(@PathParam("userId") final String userId)
	{
		User user = userService.findByUserId(userId);
		return Response.ok(user).build();
	}

	@GET
	@Path("{userId}/stories")
	@SecureUsers
	public Response getAllStoriesForUser(@PathParam("userId") final String userId)
	{
		List<Story> stories = storyService.findAllStoriesAssignedToUser(userId);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories)
		{
		};

		return Response.ok(entity).build();
	}

	@PUT
	@Path("{userId}")
	@SecureUsers
	public Response updateUser(@PathParam("userId") final String userId, User user)
	{
		user = new User(userId, user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
		userService.addOrUpdateUser(user);
		return Response.ok().build();
	}

	@DELETE
	@Path("{userId}")
	@SecureUsers
	public Response removeUser(@PathParam("userId") final String userId)
	{
		userService.removeUser(userId);
		return Response.noContent().build();
	}
}
