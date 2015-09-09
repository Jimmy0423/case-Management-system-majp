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

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.StoryService;

@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
		story = storyService.addStoryToBacklog(projectId, story);
		URI location = uriInfo.getAbsolutePathBuilder().path(story.getStoryId()).build();
		
		return Response.created(location).build();
	}
	
	@GET
	public Response getAllProjects()
	{
		List<Project> projects = projectService.findAllProjects();
		GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projects){};
		
		return Response.ok(entity).build();
	}
	
	@PUT
	@Path("{projectId}/users")
	public Response addTeamMember(@PathParam("projectId") final String projectId, TeamMember teamMember)
	{
		Project project = projectService.addOrUpdateTeamMember(projectId, teamMember);	
		return Response.ok(project).build();
	}
	
	@PUT
	@Path("{projectId}/users/{userId}")
	public Response removeTeamMember(@PathParam("projectId") final String projectId, 
									 @PathParam("userId") final String userId)
	{
		Project project = projectService.removeTeamMember(projectId, userId);
		return Response.ok(project).build();
	}
	
	@DELETE
	@Path("{projectId}")
	public Response removeProject(@PathParam("projectId") final String projectId)
	{
		projectService.removeProject(projectId);
		return Response.noContent().build();
	}
}
