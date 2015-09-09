package se.majp.caseManagement.service.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.UniqueConstraintException;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.UserService;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class UserServiceIntegrationTest extends IntegrationTestBaseClass
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private UserService userService;
	
	private User userToSave = new User(USER_ANOTHER_EMAIL, USER_PASSWORD, USER_FIRSTNAME, USER_LASTNAME);
	
	@Test
	public void addOrUpdateUser_shouldThrowUniqueConstraintException()
	{
		exception.expect(UniqueConstraintException.class);
		exception.expectMessage("email already exist");
		
		User user = new User(USER_EMAIL, USER_PASSWORD, USER_FIRSTNAME, USER_LASTNAME);
		
		userService.addOrUpdateUser(user);
	}
	
	@Test
	public void addOrUpdateUser_shouldReturnUserWithId()
	{
		User user = userService.addOrUpdateUser(userToSave);
		assertNotNull(user.getId());
	}
	
	@Test
	public void removeUser_shouldNotThrowException()
	{
		userService.removeUser(USER_USERID);
	}
	
}
