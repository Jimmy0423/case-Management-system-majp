package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class ProjectRepositoryIntegrationTest extends IntegrationTestBaseClass
{	
	@Test
	public void findByProjectId_NoMatch_ShouldReturnNull()
	{
		Project project = projectRepository.findByProjectId("NO MATCH");
		Assert.assertNull(project);
	}
	
	@Test
	public void findByProjectId_Match_ShouldReturnProject()
	{
		Project project = projectRepository.findByProjectId("projectId");
		Assert.assertThat(project, allOf(
									hasProperty("projectId", is("projectId")),
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
											hasProperty("projectId", is("projectId")),
											hasProperty("name", is("name")),
											hasProperty("description", is("description"))
											));
	}
}
