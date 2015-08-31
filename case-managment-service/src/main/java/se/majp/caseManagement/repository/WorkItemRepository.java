package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>
{
	
}
