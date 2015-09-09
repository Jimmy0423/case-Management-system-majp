package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;

public interface StoryService
{
	Story addStoryToBacklog(String projectId, Story story);

	Story addStoryToUser(String userId, Story story);

	Story addIssue(String storyId, Issue issue);

	Story changeStatus(String storyId, Status status);

	List<Story> findAllStoriesWithIssues();

	List<Story> findByDescriptionContaining(String description);

	List<Story> findBacklogForProject(String projectId);

	List<Story> findAllStoriesInProject(String projectId);

	List<Story> findAllStoriesByStatus(Status status);

	List<Story> findAllStoriesAssignedToUser(String userId);

	List<Story> findAllStoriesByUserAndProject(String userId, String projectId);

	void removeStory(String storyId);
}
