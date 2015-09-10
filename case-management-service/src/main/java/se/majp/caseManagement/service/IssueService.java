package se.majp.caseManagement.service;

import se.majp.caseManagement.model.Issue;

public interface IssueService
{
	Issue updateIssue(Issue issue, String storyId);
}
