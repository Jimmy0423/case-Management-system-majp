package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.WorkItem;
import se.majp.caseManagement.model.WorkItem.Status;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>
{
	List<WorkItem> findByStatus(Status status);

	@Query("select w from WorkItem w where w.project.team = ?1")
	List<WorkItem> findByTeam(Team team);

	List<WorkItem> findByDescriptionContaining(String description);
	
	@Query("select w from WorkItem w where size(w.issues) > 0")
	List<WorkItem> findWorkItemsWithIssues();
}
