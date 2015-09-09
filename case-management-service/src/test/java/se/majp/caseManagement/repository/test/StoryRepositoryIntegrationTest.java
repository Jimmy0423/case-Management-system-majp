package se.majp.caseManagement.repository.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.UserRepository;

public class StoryRepositoryIntegrationTest extends IntegrationTestBaseClass
{
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private IssueRepository issueRepository;
	
	private Project project;
	
	@Before
	public void setUp()
	{
		project = new Project("projectId", "name", "description");
		User user = new User("userId", "email", "password", "firstName", "lastName");
		Story story = new Story("storyId", "name", "description", project, Status.PENDING, Priority.LOW);
		Issue issue = new Issue("title", "description", story);
		story.setUser(user);
		project.getTeam().addUser(user, Role.OWNER);
		
		userRepository.save(user);
		projectRepository.save(project);
		storyRepository.save(story);
		issueRepository.save(issue);
	}
	
	@After
	public void tearDown()
	{
		projectRepository.deleteAll();
		userRepository.deleteAll();
		storyRepository.deleteAll();
		issueRepository.deleteAll();
	}
	
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
