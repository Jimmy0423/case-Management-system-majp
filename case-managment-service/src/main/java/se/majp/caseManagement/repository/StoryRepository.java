package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Story.Status;

public interface StoryRepository extends CrudRepository<Story, Long>
{
	List<Story> findByStatus(Status status);

	@Query(value = "SELECT * FROM tbl_story INNER JOIN tbl_team_story ON tbl_team_story.story_id = tbl_story.id WHERE team_id = ?1", nativeQuery = true)
	List<Story> findByTeam(Long teamId);

	List<Story> findByDescriptionContaining(String description);
	
	@Query("select s from Story s where size(s.issues) > 0")
	List<Story> findStoriesWithIssues();
}
