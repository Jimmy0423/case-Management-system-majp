package se.majp.cms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.cms.model.Project;
import se.majp.cms.model.Status;
import se.majp.cms.model.Story;

public interface StoryRepository extends CrudRepository<Story, Long>
{
	@Query("select s from Story s where s.project.projectId = ?1")
	List<Story> findByProject(String projectId);

	@Query("select s from Story s where s.user.userId = ?1")
	List<Story> findByUser(String userId);

	@Query("select s from Story s where s.user.userId = ?1 and s.project.projectId = ?2")
	List<Story> findByUserAndProject(String userId, String projectId);

	@Query("select s from Story s where size(s.issues) > 0")
	List<Story> findStoriesWithIssues();

	@Query("select s from Story s where s.project.projectId = ?1 and s.user is null")
	List<Story> findBacklogForProject(String projectId);

	Slice<Story> findByDescriptionContaining(String description, Pageable pageable);

	List<Story> findByStatus(Status status);

	List<Story> findByStatusAndProject(Status status, Project project);

	Story findByStoryId(String storyId);
	
	Slice<Story> findAll(Pageable pageable);
}
