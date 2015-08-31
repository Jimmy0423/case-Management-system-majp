package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.WorkItem;

public interface ProjectRepository extends CrudRepository<Project, Long>
{
//	@Query(select )
//	List<WorkItem> findWorkItemsByStatus(WorkItem.Status status);
	
	
}
