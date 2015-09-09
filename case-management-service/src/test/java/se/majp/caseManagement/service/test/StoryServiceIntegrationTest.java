package se.majp.caseManagement.service.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.BadRequestException;
import se.majp.caseManagement.exception.EntityNotFoundException;
import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.test.config.IntegrationTestBaseClass;

public class StoryServiceIntegrationTest extends IntegrationTestBaseClass
{
	@Autowired
	private StoryService storyService;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private Story storyToSave = new Story("name", "description", Status.PENDING, Priority.LOW);
	
	@Test
	public void addStoryToBacklog_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("No project found with that id");
		
		storyService.addStoryToBacklog("NO MATCH", storyToSave);
	}
	
	@Test
	public void addStoryToBacklog_shouldReturnStoryWithStoryId()
	{
		storyToSave = storyService.addStoryToBacklog("projectId", storyToSave);
		assertNotNull(storyToSave.getId());
	}
	
	@Test
	public void addStoryToUser_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("No user found with that id");
		
		storyService.addStoryToUser("NO MATCH", storyToSave);
	}
	
	@Test
	public void addStoryToUser_shouldReturnStoryWithUser()
	{
		Story story = storyService.addStoryToUser("userId", storyToSave);
		User user = userRepository.findByUserId("userId");
		assertThat(story.getUser(), is(user));
	}
	
	@Test
	public void addIssue_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("No story found with that storyId");
		
		storyService.addIssue("NO MATCH", new Issue("name", "description"));
	}
	
	@Test
	public void addIssue_shouldReturnStoryWithIssue()
	{
		Issue issue = new Issue("title", "description");
		Story story = storyService.addIssue("storyId", issue);
		
		assertThat(story.getIssues().size(), is(1));
		assertThat((Issue) story.getIssues().toArray()[0], allOf(
															hasProperty("title", is("title")),
															hasProperty("description", is("description"))
															));
	}
	
	@Test
	public void changeStatus_shouldThrowBadRequestException()
	{
		exception.expect(BadRequestException.class);
		exception.expectMessage("Not a valid status");
		
		storyService.changeStatus("storyId", "NO MATCH");
	}
	
	@Test
	public void changeStatus_shouldThrowEntityNotFoundException()
	{
		exception.expect(EntityNotFoundException.class);
		exception.expectMessage("No story found with that storyId");
		
		storyService.changeStatus("NO MATCH", "INPROGRESS");
	}
	
	@Test
	public void changeStatus_shouldThrowIllegalArgumentException()
	{
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("status can only be changed");
		
		storyService.changeStatus("storyId", "TEST");
	}
	
	@Test
	public void changeStatus_shouldReturnStoryWithNewStatus()
	{
		Story story = storyService.changeStatus("storyId", "INPROGRESS");
		
		assertThat(story.getStatus(), is(Status.INPROGRESS));
	}
}
