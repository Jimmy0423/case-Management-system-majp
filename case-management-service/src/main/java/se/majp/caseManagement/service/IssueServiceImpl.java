package se.majp.caseManagement.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.BadRequestException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.StoryRepository;

public class IssueServiceImpl implements IssueService
{
	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Override
	public Issue updateIssue(Issue issue, String storyId)
	{
		Story story = storyRepository.findByStoryId(storyId);

		if (issue.getIssueId() == null)
		{
			throw new BadRequestException("Issue not added, use add instead");
		}
		else if (story == null)
		{
			throw new EntityNotFoundException("No story found with that storyId");
		}

		Issue issueFromDb = issueRepository.findByIssueId(issue.getIssueId());
		issue = new Issue(issue.getIssueId(), issue.getTitle(), issue.getDescription(), story);
		issue.setId(issueFromDb.getId());

		return issueRepository.save(issue);
	}
}
