package se.majp.caseManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.exception.UniqueConstraintException;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.util.IdGenerator;

public class UserServiceImpl implements UserService
{
	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	private final IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	@Override
	public User addOrUpdateUser(User user)
	{
		if (user.getUserId() == null)
		{
			if(userRepository.findByFirstNameOrLastNameOrEmail(user.getEmail()) != null)
			{
				throw new UniqueConstraintException("User with that email already exists");
			}
			
			user = new User(idGenerator.getNextId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
		}

		return userRepository.save(user);
	}

	@Override
	public void removeUser(String userId)
	{
		User user = userRepository.findByUserId(userId);
		
		if(user == null)
		{
			throw new EntityNotFoundException("No user found with userId: " + userId);
		}
		
		projectRepository.findAllProjectsForUser(user).forEach(project -> {
			project.getTeam().removeUser(user);
			projectRepository.save(project);
		});

		user.getStories().forEach(story -> {
			removeStoryFromUser(story);
		});

		userRepository.delete(user.getId());
	}

	@Override
	public List<User> findByFirstNameOrLastNameOrEmail(String value)
	{
		List<User> users = userRepository.findByFirstNameOrLastNameOrEmail(value);

		if (users.size() > 0)
		{
			return users;
		}

		throw new EntityNotFoundException("No user matching that value");
	}

	@Override
	public User findByUserId(String userId)
	{
		User user = userRepository.findByUserId(userId);

		if (user == null)
		{
			throw new EntityNotFoundException("user not found");
		}

		return user;
	}

	@Override
	public List<User> findByProject(String projectId)
	{
		Project project = projectRepository.findByProjectId(projectId);

		if (project == null)
		{
			throw new EntityNotFoundException("project not found");
		}

		return userRepository.findByProject(projectId);
	}

	private void removeStoryFromUser(Story story)
	{
		if (story.getUser() == null)
		{
			throw new IllegalArgumentException("this story is not asigned to any user");
		}

		story.setUser(null);
		storyRepository.save(story);
	}
}
