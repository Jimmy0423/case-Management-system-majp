package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.UserRepository;

public class ProjectRepositoryIntegrationTest extends IntegrationTestBaseClass
{
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp()
	{
		Project project = new Project("p", "name", "description");
		User user = new User("userId", "email", "password", "firstName", "lastName");
		project.getTeam().addUser(user, Role.OWNER);
		
		userRepository.save(user);
		projectRepository.save(project);
	}
	
	@After
	public void tearDown()
	{
		projectRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void findByProjectId_NoMatch_ShouldReturnNull()
	{
		Project project = projectRepository.findByProjectId("NO MATCH");
		Assert.assertNull(project);
	}
	
	@Test
	public void findByProjectId_Match_ShouldReturnProject()
	{
		Project project = projectRepository.findByProjectId("p");
		Assert.assertThat(project, allOf(
									hasProperty("projectId", is("p")),
									hasProperty("name", is("name")),
									hasProperty("description", is("description"))
									));
	}
	
	@Test
	public void findAllProjectsForUser_NoMatch_shouldReturnEmptyList()
	{
		User user = userRepository.findByUserId("userId");
		List<Project> projects = projectRepository.findAllProjectsForUser(user);
		Assert.assertThat(projects.size(), is(1));
		Assert.assertThat(projects.get(0), allOf(
											hasProperty("projectId", is("p")),
											hasProperty("name", is("name")),
											hasProperty("description", is("description"))
											));
	}
}
