package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;

public interface StoryRepository extends CrudRepository<Story, Long>
{
	@Query("select s from Story s where s.project.projectId = ?1")
	List<Story> findByProject(String projectId);
	
	List<Story> findByStatus(Status status);
	
	@Query("select s from Story s where s.user.userId = ?1")
	List<Story> findByUser(String userId);
	
	@Query("select s from Story s where s.user.userId = ?1 and s.project.projectId = ?2")
	List<Story> findByUserAndProject(String userId, String projectId);
	
	@Query("select s from Story s where size(s.issues) > 0")
	List<Story> findStoriesWithIssues();
	
	List<Story> findByDescriptionContaining(String description);
}
