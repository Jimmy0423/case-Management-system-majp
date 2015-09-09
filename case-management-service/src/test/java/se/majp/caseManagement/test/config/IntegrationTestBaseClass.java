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
		Project project = new Project("projectId", "name", "description");
		User user = new User("userId", "email", "password", "firstName", "lastName");
		Story story = new Story("storyId", "name", "description", project, Status.PENDING, Priority.LOW);
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
