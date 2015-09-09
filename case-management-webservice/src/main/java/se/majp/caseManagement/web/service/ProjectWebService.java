package se.majp.caseManagement.web.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.web.auth.Authorize;

@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authorize
public class ProjectWebService
{
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private StoryService storyService;
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	public Response addProject(Project project)
	{
		Project projectFromDb = projectService.addOrUpdateProject(project);
		final URI location = uriInfo.getAbsolutePathBuilder().path(projectFromDb.getProjectId()).build();
		
		return Response.created(location).build();
	}
	
	@POST
	@Path("{projectId}/stories")
	public Response addStoryToProject(@PathParam("projectId") final String projectId, Story story)
	{
		storyService.addStoryToBacklog(projectId, story);
		return null;
	}
	
	@GET
	public Response getAllProjects()
	{
		List<Project> projects = projectService.findAllProjects();
		GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projects){};
		
		return Response.ok(entity).build();
	}
}
