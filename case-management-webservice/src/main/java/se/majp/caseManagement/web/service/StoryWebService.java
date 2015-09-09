package se.majp.caseManagement.web.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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

import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.service.StoryService;

@Path("stories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoryWebService
{	
	@Autowired
	private StoryService storyService;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	public Response getStoriesByDescription(@DefaultValue("") @QueryParam("description") final String description)
	{
		List<Story> stories = storyService.findByDescriptionContaining(description);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
		return Response.ok(entity).build();
	}
	
	@Path("status/{status}")
	@GET
	public Response getStoriesByStatus(@PathParam("status") final String status)
	{
		List<Story> stories = storyService.findAllStoriesByStatus(status);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
		return Response.ok(entity).build();
	}
	
	@GET
	@Path("issues")
	public Response getAllStoriesWithIssues()
	{
		List<Story> stories = storyService.findAllStoriesWithIssues();
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
		return Response.ok(entity).build();
	}
	
	@PUT
	@Path("{storyId}")
	public Response updateStatus(@PathParam("storyId") final String storyId, String status)
	{
		Story story = storyService.changeStatus(storyId, status);
		return Response.ok(story).build();
	}
}
