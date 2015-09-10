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
import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.service.UserService;

@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Scope("request")
public class ProjectWebService
{
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private UserService userService;

	@Context
	private UriInfo uriInfo;
	
	@POST
	public Response addProject(Project project)
	{
		Project projectFromDb = projectService.addOrUpdateProject(project);
		final URI location = uriInfo.getAbsolutePathBuilder().path(projectFromDb.getProjectId()).build();
		
		return Response.created(location).build();
	}
	
	@PUT
	public Response updateProject(Project project)
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
		
		return Response.noContent().build();
	}
	
	@GET
	public Response getAllProjects()
	{
		List<Project> projects = projectService.findAllProjects();
		GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projects){};
		
		return Response.ok(entity).build();
	}

	@GET
	@Path("{projectId}")
	public Response findByProjectId(@PathParam("projectId") final String projectId)
	{
		Project project = projectService.findByProjectId(projectId);
		return Response.ok(project).build();
	}

	@GET
	@Path("{projectId}/stories")
	public Response findAllStoriesInProject(@PathParam("projectId") final String projectId)
	{
		List<Story> stories = storyService.findAllStoriesInProject(projectId);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories)
		{
		};

		return Response.ok(entity).build();
	}

	@GET
	@Path("{projectId}/users")
	public Response findAllUsersInProject(@PathParam("projectId") final String projectId)
	{
		List<User> users = userService.findByProject(projectId);
		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users)
		{
		};

		return Response.ok(entity).build();
		
	}
	
	@GET
	@Path("{projectId}/users/{userId}/stories")
	public Response findAllStoriesForUserInProject(@PathParam("projectId") final String projectId, 
												   @PathParam("userId") final String userId)
	{
		List<Story> stories = storyService.findAllStoriesByUserAndProject(projectId, userId);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
		return Response.ok(entity).build();
	}
	
	@GET
	@Path("{projectId}/backlog")
	public Response findBacklogForProject(@PathParam("projectId") final String projectId)
	{
		List<Story> stories = storyService.findBacklogForProject(projectId);
		GenericEntity<List<Story>> entity = new GenericEntity<List<Story>>(stories){};
		
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
