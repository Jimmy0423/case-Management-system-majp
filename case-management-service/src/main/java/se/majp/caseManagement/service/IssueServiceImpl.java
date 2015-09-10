package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.BadRequestException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.util.IdGenerator;

public class IssueServiceImpl implements IssueService
{
	@Autowired
	private IssueRepository issueRepository;
	
	private IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	@Override
	public Issue addIssue(Issue issue)
	{
		if(issue.getIssueId() == null)
		{
			issue = new Issue(idGenerator.getNextId(), issue.getTitle(), issue.getDescription());
			return issueRepository.save(issue);
		}
		
		throw new BadRequestException("Issue already added, use update instead");
	}

	@Override
	public Issue updateIssue(Issue issue)
	{
		if(issue.getIssueId() == null)
		{
			throw new BadRequestException("Issue not added, use add instead");
		}
		
		Issue issueFromDb = issueRepository.findByIssueId(issue.getIssueId());
		issue.setId(issueFromDb.getId());
		
		return issueRepository.save(issue);
	}
}
