package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Story.Status;
import se.majp.caseManagement.model.User;

public interface StoryRepository extends CrudRepository<Story, Long>
{
	List<Story> findByStatus(Status status);

	@Query("select p.stories from Project p where p = ?1")
	List<Story> findByProject(Project project);
	
	@Query("select t.stories from TeamMember t where t.user = ?1")
	List<Story> findByUser(User user);

	List<Story> findByDescriptionContaining(String description);
	
	@Query("select s from Story s where size(s.issues) > 0")
	List<Story> findStoriesWithIssues();
}
