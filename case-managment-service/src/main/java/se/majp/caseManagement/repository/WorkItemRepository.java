package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.WorkItem;
import se.majp.caseManagement.model.WorkItem.Status;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>
{
	List<WorkItem> findByStatus(Status status);

	List<WorkItem> findByDescription(String description);
}
