package se.majp.cms.service;

import java.util.List;

import se.majp.cms.model.Issue;
import se.majp.cms.model.Story;

public interface StoryService
{
	Story addStoryToBacklog(String projectId, Story story);

	Story updateStory(String storyId, Story story);
	
	Story addStoryToUser(String userId, String storyId);

	Story addIssue(String storyId, Issue issue);

	List<Story> findAllStoriesWithIssues();

	List<Story> findByDescriptionContaining(String description);

	List<Story> findBacklogForProject(String projectId);

	List<Story> findAllStoriesInProject(String projectId);

	List<Story> findAllStoriesByStatus(String status);

	List<Story> findAllStoriesAssignedToUser(String userId);

	List<Story> findAllStoriesByUserAndProject(String userId, String projectId);

	void removeStory(String storyId);
}
