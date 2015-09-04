package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.UserRepository;

@Service
public class UserService
{
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user)
	{
		return userRepository.save(user);
	}
}
