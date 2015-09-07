package se.majp.caseManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.util.IdGenerator;

public class UserService
{
	private IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public User addUser(User user)
	{
		if (user.getUserId() == null)
		{
			User userToSave = new User(idGenerator.getNextId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
			return userRepository.save(userToSave);
		}
		else
		{
			return userRepository.save(user);
		}
	}

	public void removeUser(User user)
	{
		projectRepository.findAllProjectsForUser(user).forEach(project -> {
			project.getTeam().removeUser(user);
			projectRepository.save(project);
		});

		user.getStories().forEach(story -> {
			removeStoryFromUser(story);
		});

		userRepository.delete(user.getId());
	}

	public User findByFirstNameOrLastNameOrEmail(String value)
	{
		List<User> dbUser = userRepository.findByFirstNameOrLastNameOrEmail(value);
		if (dbUser.size() == 1)
		{
			return dbUser.get(0);
		}
		throw new EntityNotFoundException("user not found");
	}

	public User findByUserId(String userId)
	{
		List<User> dbUser = userRepository.findByUserId(userId);
		if (dbUser.size() == 1)
		{
			return dbUser.get(0);
		}
		throw new EntityNotFoundException("user not found");
	}

	public User findByProject(String projectId)
	{
		List<User> dbUser = userRepository.findByUserId(projectId);
		if (dbUser.size() == 1)
		{
			return dbUser.get(0);
		}
		throw new EntityNotFoundException("user not found");
	}

	public Story findAllStories(String userId)
	{
		if (findByUserId(userId) != null)
		{
			List<Story> dbStory = storyRepository.findByUser(userId);
			return dbStory.get(0);
		}
		throw new EntityNotFoundException("user not found");
	}

	public Story findAllStoriesByProject(String userId, String projectId)
	{
		if (findByUserId(userId) != null)
		{
			List<Story> dbStory = storyRepository.findByUserAndProject(userId, projectId);
			return dbStory.get(0);
		}
		throw new EntityNotFoundException("user not found");
	}

	public User addStory(User user, Story story)
	{
		story.setUser(user);
		storyRepository.save(story);

		return userRepository.findByUserId(user.getUserId()).get(0);
	}

	public void removeStoryFromUser(Story story)
	{
		if (story.getUser() == null)
		{
			throw new IllegalArgumentException("this story is already not asigned to any user");
		}

		story.setUser(null);
		storyRepository.save(story);
	}
}
