package se.majp.caseManagement.test.config;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import se.majp.caseManagement.config.ServiceConfig;
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

@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@ContextConfiguration(classes = { TestConfig.class,
		ServiceConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class IntegrationTestBaseClass
{
	protected static final String USER_USERID = "a1fRhW43p";
	protected static final String USER_EMAIL = "email@example.com";
	protected static final String USER_ANOTHER_EMAIL = "another@example.com";
	protected static final String USER_PASSWORD = "password";
	protected static final String USER_FIRSTNAME = "John";
	protected static final String USER_LASTNAME = "Doe";
	protected static final String PROJECT_PROJECTID = "gHjMnB7Y";
	protected static final String PROJECT_NAME = "Great system";
	protected static final String PROJECT_DESCRIPTION = "Let's build a great system";
	protected static final String STORY_STORYID = "aHjJj43p";
	protected static final String STORY_NAME = "Do stuff";
	protected static final String STORY_DESCRIPTION = "Make it great!";
	protected static final Status STORY_STATUS = Status.PENDING;
	protected static final String ISSUE_NAME = "You got it wrong";
	protected static final String ISSUE_DESCRIPTION = "Change the constructor";
	protected static final Priority STORY_PRIORITY = Priority.LOW;
	protected static final String ISSUE_TITLE = "BUG";
	protected static final String ISSUE_DESCRIPTION = "Not working";
	
	@Autowired
	protected StoryRepository storyRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ProjectRepository projectRepository;

	@Autowired
	protected IssueRepository issueRepository;
	
	@Before
	public void setUp()
	{
		Project project = new Project(PROJECT_PROJECTID, PROJECT_NAME, PROJECT_DESCRIPTION);
		User user = new User(USER_USERID, USER_EMAIL, USER_PASSWORD, USER_FIRSTNAME, USER_LASTNAME);
		Story story = new Story(STORY_STORYID, STORY_NAME, STORY_DESCRIPTION, project, STORY_STATUS, STORY_PRIORITY);
		
		story.setUser(user);
		project.getTeam().addUser(user, Role.OWNER);

		userRepository.save(user);
		projectRepository.save(project);
		storyRepository.save(story);
	}
	
	@After
	public void tearDown()
	{
		projectRepository.deleteAll();
		storyRepository.deleteAll();
		userRepository.deleteAll();
		issueRepository.deleteAll();
	}
}
