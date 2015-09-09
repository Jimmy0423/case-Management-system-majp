package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

public class UserRepositoryIntegrationTest extends IntegrationTestBaseClass
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Before
	public void setUp()
	{
		User user = new User("a", "email", "password", "firstName", "lastName");
		Project project = new Project("p", "name", "description");
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
	public void findByFirstNameOrLastNameOrEmail_NoMatch_shouldReturnEmptyList()
	{
		List<User> users = userRepository.findByFirstNameOrLastNameOrEmail("NO MATCH");
		assertThat(users.size(), is(0));
	}
	
	@Test
	public void findByFirstNameOrLastNameOrEmail_OneMatch_shouldReturnListOfSizeOne()
	{
		List<User> users = userRepository.findByFirstNameOrLastNameOrEmail("firstName");
		assertThat(users.size(), is(1));
		assertThat(users.get(0), allOf(
					hasProperty("userId", is("a")),
					hasProperty("email", is("email")),
					hasProperty("password", is("password")),
					hasProperty("firstName", is("firstName")),
					hasProperty("lastName", is("lastName"))
				));
	}
	
	@Test
	public void findByUserId_NoMatch_shouldReturnNull()
	{
		User user = userRepository.findByUserId("NO MATCH");
		Assert.assertNull(user);
	}
	
	@Test
	public void findByUserId_Match_shouldReturnUser()
	{
		User user = userRepository.findByUserId("a");
		assertThat(user.getUserId(), is("a"));
	}
	
	@Test
	public void findByEmail_NoMatch_shouldReturnNull()
	{
		User user = userRepository.findByEmail("NO MATCH");
		Assert.assertNull(user);
	}
	
	@Test
	public void findByEmail_Match_shouldReturnUser()
	{
		User user = userRepository.findByEmail("email");
		assertThat(user.getEmail(), is("email"));
	}
	
	@Test
	public void findByProject_NoMatch_shouldReturnEmptyList()
	{
		List<User> users = userRepository.findByProject("NO MATCH");
		assertThat(users.size(), is(0));
	}
	
	@Test
	public void findByProject_OneMatch_shouldReturnListOfSizeOne()
	{
		List<User> users = userRepository.findByProject("p");
		assertThat(users.size(), is(1));
		assertThat(users.get(0), allOf(
				hasProperty("userId", is("a")),
				hasProperty("email", is("email")),
				hasProperty("password", is("password")),
				hasProperty("firstName", is("firstName")),
				hasProperty("lastName", is("lastName"))
			));
	}
}
