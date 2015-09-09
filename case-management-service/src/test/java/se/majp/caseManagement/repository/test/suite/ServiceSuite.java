package se.majp.caseManagement.repository.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.majp.caseManagement.service.test.ProjectServiceIntegrationTest;
import se.majp.caseManagement.service.test.StoryServiceIntegrationTest;
import se.majp.caseManagement.service.test.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ProjectServiceIntegrationTest.class,
			   UserServiceIntegrationTest.class,
			   StoryServiceIntegrationTest.class
			 })
public class ServiceSuite
{
}
