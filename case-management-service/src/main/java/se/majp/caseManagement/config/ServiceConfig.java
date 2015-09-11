package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.majp.caseManagement.service.IssueService;
import se.majp.caseManagement.service.IssueServiceImpl;
import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.ProjectServiceImpl;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.service.StoryServiceImpl;
import se.majp.caseManagement.service.UserService;
import se.majp.caseManagement.service.UserServiceImpl;

@Configuration
@EnableJpaRepositories("se.majp.caseManagement.repository")
public class ServiceConfig
{
	@Bean
	public UserService userService()
	{
		return new UserServiceImpl();
	}

	@Bean
	public ProjectService projectService()
	{
		return new ProjectServiceImpl();
	}

	@Bean
	public StoryService storyService()
	{
		return new StoryServiceImpl();
	}

	@Bean
	public IssueService issueService()
	{
		return new IssueServiceImpl();
	}
}
