package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import se.majp.caseManagement.model.User;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class UserRepositoryIntegrationTest extends IntegrationTestBaseClass
{	
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
					hasProperty("userId", is("userId")),
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
		User user = userRepository.findByUserId("userId");
		assertThat(user.getUserId(), is("userId"));
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
		List<User> users = userRepository.findByProject("projectId");
		assertThat(users.size(), is(1));
		assertThat(users.get(0), allOf(
				hasProperty("userId", is("userId")),
				hasProperty("email", is("email")),
				hasProperty("password", is("password")),
				hasProperty("firstName", is("firstName")),
				hasProperty("lastName", is("lastName"))
			));
	}
}
