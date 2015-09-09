package se.majp.caseManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.exception.PermissionDeniedException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.util.IdGenerator;

public class StoryService
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
	
	public Story addStoryToBacklog(User user, String projectId, Story story)
	{
		Project project = projectRepository.findByProjectId(projectId);
		
		if(project == null)
		{
			throw new EntityNotFoundException("Project doesn't exist");
		}
		
		if(project.getTeam().userHasRole(user, Role.OWNER))
		{
			story = new Story(idGenerator.getNextId(), story.getName(), story.getDescription(), project, story.getStatus(), story.getPriority());
			return storyRepository.save(story);
		}
		
		throw new PermissionDeniedException("User is not an owner");
	}
	
	public Story addStoryToUser(User user, Story story)
	{
		story.setUser(user);
		return storyRepository.save(story);
	}
	
	public Story addIssue(Story story, Issue issue)
	{
		Issue issueToSave = new Issue(issue.getTitle(), issue.getDescription(), story);
		issueRepository.save(issueToSave);
		
		return storyRepository.findByStoryId(story.getStoryId());
	}
	
	public Story changeStatus(Story story, Status status)
	{
		switch(story.getStatus())
		{
		case PENDING:
			switch(status)
			{
			case INPROGRESS:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("status can only be changed to INPROGRESS");
			}
			break;
		
		case ISSUED:
			switch(status)
			{
			case TEST:
				story.setUser(null);
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to TEST");
			}
			break;
		
		case INPROGRESS:
			switch(status)
			{
			case TEST:
				story.setUser(null);
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to TEST");
			}
			break;
		
		case TEST:
			switch(status)
			{
			case DONE: 
				story.setUser(null);
				story.changeStatus(status);
				break;
			case ISSUED:
				story.setUser(null);
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to TEST or ISSUED");
			}
			break;
		
		case DONE:
			switch(status)
			{
			case ISSUED:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to ISSUED");
			}
			break;
			
		default:
			throw new IllegalArgumentException("Not a valid status");
		}
		
		return storyRepository.save(story);
	}
	
	public List<Story> findAllStoriesWithIssues()
	{
		return storyRepository.findStoriesWithIssues();
	}
	
	public List<Story> findByDescriptionContaining(String description)
	{
		List<Story> stories = storyRepository.findByDescriptionContaining(description);
		if (stories.isEmpty())
		{
			throw new EntityNotFoundException("No stories matching description");
		}
		
		return stories;
	}
	
	public List<Story> findBacklogForProject(String projectId)
	{
		if(projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findBacklogForProject(projectId);
		}
		
		throw new EntityNotFoundException("Project not in DB");
	}

	public List<Story> findAllStoriesInProject(String projectId)
	{
		if(projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findByProject(projectId);
		}
		
		throw new EntityNotFoundException("Project not in DB");
	}
	
	public List<Story> findAllStoriesByStatus(Status status)
	{
		return storyRepository.findByStatus(status);
	}
	
	public Story findByStoryId(String storyId)
	{
		Story story = storyRepository.findByStoryId(storyId);
		
		if (story == null)
		{
			throw new EntityNotFoundException("No story with matching storyId");
		}
		
		return story;
	}
	
	public List<Story> findAllStoriesAssignedToUser(String userId)
	{
		if (userRepository.findByUserId(userId) != null)
		{
			List<Story> stories = storyRepository.findByUser(userId);
			return stories;
		}

		throw new EntityNotFoundException("user not found");
	}

	public List<Story> findAllStoriesByUserAndProject(String userId, String projectId)
	{
		if (userRepository.findByUserId(userId) != null)
		{
			List<Story> stories = storyRepository.findByUserAndProject(userId, projectId);
			return stories;
		}

		throw new EntityNotFoundException("user not found");
	}
	
	public void removeStoryFromBacklog(User user, Project project, Story story)
	{
		if(project.getTeam().userHasRole(user, Role.OWNER))
		{
			storyRepository.delete(story);
		}
		
		throw new PermissionDeniedException("User is not an owner");
	}
}
