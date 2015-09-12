package se.majp.cms.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import se.majp.cms.model.Issue;
import se.majp.cms.model.Story;

public interface StoryService
{
	Story addStoryToBacklog(String projectId, Story story);

	Story addStoryToUser(String userId, Story story);

	Story addIssue(String storyId, Issue issue);

	Story changeStatus(String storyId, String status);

	List<Story> findAllStoriesWithIssues();

	Slice<Story> findByDescriptionContaining(String description, Pageable pageable);

	List<Story> findBacklogForProject(String projectId);

	List<Story> findAllStoriesInProject(String projectId);

	List<Story> findAllStoriesByStatus(String status);

	List<Story> findAllStoriesAssignedToUser(String userId);

	List<Story> findAllStoriesByUserAndProject(String userId, String projectId);

	void removeStory(String storyId);
	
	Slice<Story> findAllStories(Pageable pageable);
}
