package se.majp.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.cms.exception.BadRequestException;
import se.majp.cms.exception.EntityNotFoundException;
import se.majp.cms.model.Issue;
import se.majp.cms.model.Project;
import se.majp.cms.model.Status;
import se.majp.cms.model.Story;
import se.majp.cms.model.User;
import se.majp.cms.repository.IssueRepository;
import se.majp.cms.repository.ProjectRepository;
import se.majp.cms.repository.StoryRepository;
import se.majp.cms.repository.UserRepository;
import se.majp.cms.util.IdGenerator;

public class StoryServiceImpl implements StoryService
{
	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	private final IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	@Override
	public Story addStoryToBacklog(String projectId, Story story)
	{
		Project project = projectRepository.findByProjectId(projectId);

		if (project == null)
		{
			throw new EntityNotFoundException("No project found with that id");
		}

		story = new Story(idGenerator.getNextId(), story.getName(), story.getDescription(), project, story.getStatus(), story.getPriority());
		return storyRepository.save(story);
	}
	
	@Override
	public Story updateStory(String storyId, Story story)
	{
		Story storyToUpdate = storyRepository.findByStoryId(storyId);

		if (storyToUpdate == null)
		{
			throw new EntityNotFoundException("No story found with that id");
		}

		storyToUpdate.setName(story.getName());
		storyToUpdate.setDescription(story.getDescription());
		storyToUpdate.changeStatus(story.getStatus());
		storyToUpdate.setPriority(story.getPriority());
		return storyRepository.save(storyToUpdate);
	}

	@Override
	public Story addStoryToUser(String userId, Story story)
	{
		User user = userRepository.findByUserId(userId);
		story = storyRepository.findByStoryId(story.getStoryId());

		if (user == null)
		{
			throw new EntityNotFoundException("No user found with that id");
		}

		story.setUser(user);
		return storyRepository.save(story);
	}

	@Override
	public Story addIssue(String storyId, Issue issue)
	{
		Story story = storyRepository.findByStoryId(storyId);

		if (story == null)
		{
			throw new EntityNotFoundException("No story found with that storyId");
		}

		issue = new Issue(idGenerator.getNextId(), issue.getTitle(), issue.getDescription(), story);
		issueRepository.save(issue);

		return storyRepository.findByStoryId(storyId);
	}

	@Override
	public List<Story> findAllStoriesWithIssues()
	{
		return storyRepository.findStoriesWithIssues();
	}

	@Override
	public List<Story> findByDescriptionContaining(String description)
	{
		List<Story> stories = storyRepository.findByDescriptionContaining(description);
		if (stories.isEmpty())
		{
			throw new EntityNotFoundException("No stories matching description");
		}

		return stories;
	}

	@Override
	public List<Story> findBacklogForProject(String projectId)
	{
		if (projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findBacklogForProject(projectId);
		}

		throw new EntityNotFoundException("Project not in DB");
	}

	@Override
	public List<Story> findAllStoriesInProject(String projectId)
	{
		if (projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findByProject(projectId);
		}

		throw new EntityNotFoundException("Project not in DB");
	}

	@Override
	public List<Story> findAllStoriesByStatus(String status)
	{
		if (isValidStatus(status))
		{
			return storyRepository.findByStatus(Status.valueOf(status));
		}

		throw new BadRequestException("Not a valid status");
	}

	@Override
	public List<Story> findAllStoriesAssignedToUser(String userId)
	{
		if (userRepository.findByUserId(userId) != null)
		{
			List<Story> stories = storyRepository.findByUser(userId);
			return stories;
		}

		throw new EntityNotFoundException("user not found");
	}

	@Override
	public List<Story> findAllStoriesByUserAndProject(String projectId, String userId)
	{
		if (userRepository.findByUserId(userId) != null)
		{
			List<Story> stories = storyRepository.findByUserAndProject(userId, projectId);

			return stories;
		}

		throw new EntityNotFoundException("user not found");
	}

	@Override
	public void removeStory(String storyId)
	{
		Story story = storyRepository.findByStoryId(storyId);

		if (story == null)
		{
			throw new EntityNotFoundException("No story found with that storyId");
		}

		storyRepository.delete(story);
	}

	private boolean isValidStatus(String stringStatus)
	{
		for (Status status : Status.values())
		{
			if (status.toString().equals(stringStatus))
			{
				return true;
			}
		}

		return false;
	}
}
