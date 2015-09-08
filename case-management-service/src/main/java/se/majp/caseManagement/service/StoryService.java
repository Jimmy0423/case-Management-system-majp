package se.majp.caseManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.StoryRepository;

public class StoryService
{	
	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	IssueRepository issueRepository;
	
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
		List<Story> stories = storyRepository.findStoriesWithIssues();
		if (stories == null || stories.isEmpty())
		{
			throw new EntityNotFoundException("No stories with issues");
		}
		
		return stories;
	}
	
	public List<Story> findByDescriptionContaining(String description)
	{
		List<Story> stories = storyRepository.findByDescriptionContaining(description);
		if (stories == null || stories.isEmpty())
		{
			throw new EntityNotFoundException("No stories matching description");
		}
		
		return stories;
	}
	
	public List<Story> findAllStoriesByStatus(Status status)
	{
		List<Story> stories = storyRepository.findByStatus(status);
		if (stories == null || stories.isEmpty())
		{
			throw new EntityNotFoundException("No stories with matching status");
		}
		
		return stories;
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
	
	// TODO re think logic, how should it work
	public void removeStory(User user, Story story)
	{
		if (story.getUser() == user)
		{
//			user.getStories().remove(story);
			storyRepository.delete(story);
		}
	}
}
