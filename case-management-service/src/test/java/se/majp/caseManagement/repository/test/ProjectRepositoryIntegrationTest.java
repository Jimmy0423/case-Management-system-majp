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
		Project project = projectRepository.findByProjectId(PROJECT_PROJECTID);
		Assert.assertThat(project, allOf(
									hasProperty("projectId", is(PROJECT_PROJECTID)),
									hasProperty("name", is(PROJECT_NAME)),
									hasProperty("description", is(PROJECT_DESCRIPTION))
									));
	}
	
	@Test
	public void findAllProjectsForUser_NoMatch_shouldReturnEmptyList()
	{
		User user = userRepository.findByUserId(USER_USERID);
		List<Project> projects = projectRepository.findAllProjectsForUser(user);
		Assert.assertThat(projects.size(), is(1));
		Assert.assertThat(projects.get(0), allOf(
											hasProperty("projectId", is(PROJECT_PROJECTID)),
											hasProperty("name", is(PROJECT_NAME)),
											hasProperty("description", is(PROJECT_DESCRIPTION))
											));
	}
}
