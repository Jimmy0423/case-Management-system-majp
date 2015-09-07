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

	public User addStory(User user, Story story)
	{
		story.setUser(user);
		storyRepository.save(story);

		return userRepository.findByUserId(user.getUserId());
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
