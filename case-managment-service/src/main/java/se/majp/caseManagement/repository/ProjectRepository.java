package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>
{
}
