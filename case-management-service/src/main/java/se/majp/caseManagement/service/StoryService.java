package se.majp.caseManagement.service;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Status;

public class StoryService
{
	public Story addIssue(Story story, Issue issue)
	{
		return story.addIssue(issue);
	}
	
	public Story changeStatus(Story story, Status status)
	{
		switch(story.getStatus())
		{
		case DONE:
			switch (status)
			{
			case ISSUED:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to ISSUED");
			}
			break;
			
		case INPROGRESS:
			switch (status)
			{
			case DONE:
				story.changeStatus(status);
				break;
			case PENDING:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to DONE or PENDING");
			}
			break;
			
		case ISSUED:
			switch (status)
			{
			case INPROGRESS:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to INPROGRESS");
			}
			break;
			
		case PENDING:
			switch (status)
			{
			case INPROGRESS:
				story.changeStatus(status);
				break;
			default:
				throw new IllegalArgumentException("Status can only be changed to INPROGRESS");
			}
			break;
			
		default:
			throw new IllegalArgumentException("Not a valid status");
		}
		
		return story;
	}
}
