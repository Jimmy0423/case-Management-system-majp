package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
public interface StoryService
{
	Story addStoryToBacklog(User user, Project project, Story story);

	Story addStoryToUser(User user, Story story);

	Story addIssue(Story story, Issue issue);

	Story changeStatus(Story story, Status status);

	List<Story> findAllStoriesWithIssues();

	List<Story> findByDescriptionContaining(String description);

	List<Story> findBacklogForProject(String projectId);

	List<Story> findAllStoriesInProject(String projectId);

	List<Story> findAllStoriesByStatus(Status status);

	Story findByStoryId(String storyId);

	List<Story> findAllStoriesAssignedToUser(String userId);

	List<Story> findAllStoriesByUserAndProject(String userId, String projectId);

	void removeStoryFromBacklog(User user, Project project, Story story);
}
