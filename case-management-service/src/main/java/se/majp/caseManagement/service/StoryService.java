package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
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
}
