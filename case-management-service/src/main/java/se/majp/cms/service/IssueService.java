package se.majp.cms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import se.majp.cms.model.Issue;

public interface IssueService
{
	Issue updateIssue(Issue issue, String storyId);
	
	Slice<Issue> findAllIssues(Pageable pageable);
}
