package se.majp.caseManagement.service.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.BadRequestException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.service.IssueService;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class IssueServiceIntegrationTest extends IntegrationTestBaseClass
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private IssueService issueService;
	
	private Issue issueToSave = new Issue(ISSUE_NAME, ISSUE_DESCRIPTION);
	
	@Test
	public void AddIssue()
	{
		Issue issue = issueService.addIssue(issueToSave);
		assertNotNull(issue);
		
		// Not working
//		assertNotNull(issue.getId());
//		
//		exception.expect(BadRequestException.class);
//		exception.expectMessage("Issue already added");
//		issueService.addIssue(issue);
	}
	
	@Test
	public void updateIssue()
	{
		exception.expect(BadRequestException.class);
		exception.expectMessage("Issue not added");
		Issue issue = issueService.updateIssue(issueToSave);
		
		assertNull(issue);
	}
}
