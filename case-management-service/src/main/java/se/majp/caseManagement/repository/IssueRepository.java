package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long>
{
}
