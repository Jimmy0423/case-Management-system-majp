package se.majp.cms.web.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import se.majp.cms.model.Issue;
import se.majp.cms.model.Story;
import se.majp.cms.service.IssueService;
import se.majp.cms.service.StoryService;

@Path("stories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Scope("request")
public class StoryWebService
{
	@Autowired
	private StoryService storyService;

	@Autowired
	private IssueService issueService;

	@Context
	private UriInfo uriInfo;

	@POST
	@Path("{storyId}/issues")
	public Response addIssueToStory(@PathParam("storyId") final String storyId, Issue issue)
	{
		storyService.addIssue(storyId, issue);
		return Response.noContent().build();
	}

	@GET
	public Response getStoriesByDescription(@DefaultValue("") @QueryParam("description") final String description)
	{
		List<Story> stories = storyService.findByDescriptionContaining(description);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories)
		{
		};

		return Response.ok(entity).build();
	}

	@Path("status/{status}")
	@GET
	public Response getStoriesByStatus(@PathParam("status") final String status)
	{
		List<Story> stories = storyService.findAllStoriesByStatus(status.toUpperCase());
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories)
		{
		};

		return Response.ok(entity).build();
	}

	@GET
	@Path("issues")
	public Response getAllStoriesWithIssues()
	{
		List<Story> stories = storyService.findAllStoriesWithIssues();
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories)
		{
		};

		return Response.ok(entity).build();
	}

	@PUT
	@Path("{storyId}")
	public Response updateStory(@PathParam("storyId") final String storyId, Story story)
	{
		storyService.updateStory(storyId, story);
		return Response.ok().build();
	}

	@PUT
	@Path("{storyId}/issues/{issueId}")
	public Response updateIssue(@PathParam("storyId") final String storyId,
			@PathParam("issueId") final String issueId, Issue issue)
	{
		issue = new Issue(issueId, issue.getTitle(), issue.getDescription());
		issueService.updateIssue(issue, storyId);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{storyId}/issues/{issueId}")
	public Response removeIssue(@PathParam("storyId") final String storyId, 
								@PathParam("issueId") final String issueId)
	{
		issueService.removeIssue(issueId);
		return Response.ok().build();
	}

	@DELETE
	@Path("{storyId}")
	public Response removeStory(@PathParam("storyId") final String storyId)
	{
		storyService.removeStory(storyId);
		return Response.noContent().build();
	}
}
