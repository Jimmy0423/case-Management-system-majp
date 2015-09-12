package se.majp.cms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import se.majp.cms.model.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long>
{
	Issue findByIssueId(String issueId);
	
	Slice<Issue> findAll(Pageable pageable);
}
