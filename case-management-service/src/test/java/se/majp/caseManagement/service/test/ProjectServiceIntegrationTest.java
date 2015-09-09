package se.majp.caseManagement.service.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class ProjectServiceIntegrationTest extends IntegrationTestBaseClass
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private ProjectService projectService;
	
	private Project projectToSave = new Project("name", "description");
	
	@Test
	public void addOrUpdateProject_shouldReturnProjectWithId()
	{
		Project project = projectService.addOrUpdateProject(projectToSave);
		assertNotNull(project.getId());
	}
	
	@Test
	public void addOrUpdateTeamMember_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("No project found");
		
		projectService.addOrUpdateTeamMember("NO MATCH", null);
	}
	
	@Test
	public void addOrUpdateTeamMember_shouldReturnProjectWithUser()
	{
		User user = userRepository.findByUserId("userId");
		Project project = projectService.addOrUpdateTeamMember("projectId", new TeamMember(user, Role.OWNER));
		
		assertThat(project.getTeam().getUsers().size(), is(1));
	}
	
	@Test
	public void removeTeamMember_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("not found");
		
		projectService.removeTeamMember("NO MATCH", "NO MATCH");
	}
	
	@Test
	public void removeTeamMember_shouldReturnProjectWithNoUsers()
	{
		Project project = projectService.findByProjectId("projectId");
		
		assertThat(project.getTeam().getUsers().size(), is(1));
		
		project = projectService.removeTeamMember(project.getProjectId(), "userId");
		
		assertThat(project.getTeam().getUsers().size(), is(0));
	}
}
