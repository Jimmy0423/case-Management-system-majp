package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

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
		throw new IllegalArgumentException("user already exist");
	}

	public void removeUser(User user)
	{
		user.getStories().forEach(story -> {
			story.setUser(null);
		});
		projectRepository.findAllProjectsForUser(user).forEach(project -> {
			project.getTeam().removeUser(user);
		});
		storyRepository.save(user.getStories());
		userRepository.delete(user);
	}

	public User updateUser(User user)
	{
		if (user.getUserId() == null)
		{
			throw new IllegalArgumentException("user does not exist, can't update user");
		}
		return userRepository.save(user);

	}

	public User addStory(User user, Story story)
	{
		story.setUser(user);
		storyRepository.save(story);

		return story.getUser();
	}

	public void removeStory(Story story)
	{
		if (story.getUser() == null)
		{
			throw new IllegalArgumentException("");
		}
		story.setUser(null);
		storyRepository.save(story);
	}
}
