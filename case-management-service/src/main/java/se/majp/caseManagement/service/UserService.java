package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.User;

public interface UserService
{
	User addOrUpdateUser(User user);

	void removeUser(String userId);

	List<User> findByFirstNameOrLastNameOrEmail(String value);

	User findByUserId(String userId);

	List<User> findByProject(String projectId);
}
