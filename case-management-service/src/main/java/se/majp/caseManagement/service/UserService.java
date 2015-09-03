package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.UserRepository;

public class UserService
{
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user)
	{
		return userRepository.save(user);
	}
}
