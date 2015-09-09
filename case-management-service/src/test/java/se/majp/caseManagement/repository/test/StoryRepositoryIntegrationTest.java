package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class StoryRepositoryIntegrationTest extends IntegrationTestBaseClass
{	
	@Test
	public void findByProject_NoMatch_shouldReturnEmptyList()
	{
		List<Story> stories = storyRepository.findByProject("NO MATCH");
		assertThat(stories.size(), is(0));
	}
	
	@Test
	public void findByProject_Match_shouldReturnListOfSizeOne()
	{
		List<Story> stories = storyRepository.findByProject("projectId");
		checkSizeOneAndStoryProperties(stories);
	}
	
	@Test
	public void findByUser_NoMatch_shouldReturnEmptyList()
	{
		List<Story> stories = storyRepository.findByUser("NO MATCH");
		assertThat(stories.size(), is(0));
	}
	
	@Test
	public void findByUser_Match_shouldReturnListOfSizeOne()
	{
		List<Story> stories = storyRepository.findByUser("userId");
		checkSizeOneAndStoryProperties(stories);
	}
	
	@Test
	public void findByUserAndProject_NoMatch_shouldReturnEmptyList()
	{
		List<Story> stories = storyRepository.findByUserAndProject("NO MATCH", "NO MATCH");
		assertThat(stories.size(), is(0));
	}
	
	@Test
	public void findByUserAndProject_Match_shouldReturnListOfSizeOne()
	{
		List<Story> stories = storyRepository.findByUserAndProject("userId", "projectId");
		checkSizeOneAndStoryProperties(stories);
	}
	
	@Test
	public void findStoriesWithIssues_Match_shouldReturnListOfSizeOne()
	{
		Story story = storyRepository.findByStoryId("storyId");
		Issue issue = new Issue("name", "description", story);
		issueRepository.save(issue);
		
		List<Story> stories = storyRepository.findStoriesWithIssues();
		checkSizeOneAndStoryProperties(stories);
	}
	
	@Test
	public void findBacklogForProject_NoMatch_shouldReturnEmptyList()
	{
		List<Story> stories = storyRepository.findBacklogForProject("NO MATCH");
		assertThat(stories.size(), is(0));
	}
	
	@Test
	public void findBacklogForProject_Match_shouldReturnListOfSizeOne()
	{
		Story story = storyRepository.findByStoryId("storyId");
		story.setUser(null);
		storyRepository.save(story);
		
		List<Story> stories = storyRepository.findBacklogForProject("projectId");
		checkSizeOneAndStoryProperties(stories);
	}
	
	private void checkSizeOneAndStoryProperties(List<Story> stories)
	{
		Project project = projectRepository.findByProjectId("projectId");
		
		assertThat(stories.size(), is(1));
		assertThat(stories.get(0), allOf(
									hasProperty("storyId", is("storyId")),
									hasProperty("name", is("name")),
									hasProperty("description", is("description")),
									hasProperty("project", is(project)),
									hasProperty("status", is(Status.PENDING)),
									hasProperty("priority", is(Priority.LOW))
								));
	}
}
