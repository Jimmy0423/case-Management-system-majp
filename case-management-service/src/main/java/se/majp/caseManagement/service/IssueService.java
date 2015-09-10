package se.majp.caseManagement.service;

import se.majp.caseManagement.model.Issue;

public interface IssueService
{
	Issue addIssue(Issue issue);

	Issue updateIssue(Issue issue);
}
