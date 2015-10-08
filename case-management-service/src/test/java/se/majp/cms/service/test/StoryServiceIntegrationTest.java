package se.majp.cms.service.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import se.majp.cms.exception.EntityNotFoundException;
import se.majp.cms.model.Issue;
import se.majp.cms.model.Story;
import se.majp.cms.model.User;
import se.majp.cms.service.StoryService;
import se.majp.cms.test.config.IntegrationTestBaseClass;

public class StoryServiceIntegrationTest extends IntegrationTestBaseClass
{
	@Autowired
	private StoryService storyService;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private Story storyToSave = new Story(STORY_NAME, STORY_DESCRIPTION, STORY_STATUS, STORY_PRIORITY);

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
		storyToSave = storyService.addStoryToBacklog(PROJECT_PROJECTID, storyToSave);
		assertNotNull(storyToSave.getId());
	}

	@Test
	public void addIssue_shouldReturnStoryWithIssue()
	{
		Story story = storyService.addIssue(STORY_STORYID, new Issue(ISSUE_TITLE, ISSUE_DESCRIPTION));

		assertThat(story.getIssues().size(), is(1));
		assertThat((Issue) story.getIssues().toArray()[0], allOf(
				hasProperty("title", is(ISSUE_TITLE)),
				hasProperty("description", is(ISSUE_DESCRIPTION))));
	}
}
