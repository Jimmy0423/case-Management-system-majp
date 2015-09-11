package se.majp.caseManagement.repository.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.majp.caseManagement.repository.test.ProjectRepositoryIntegrationTest;
import se.majp.caseManagement.repository.test.StoryRepositoryIntegrationTest;
import se.majp.caseManagement.repository.test.UserRepositoryIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({
		ProjectRepositoryIntegrationTest.class,
		UserRepositoryIntegrationTest.class,
		StoryRepositoryIntegrationTest.class
})
public class RepositorySuite
{

}
