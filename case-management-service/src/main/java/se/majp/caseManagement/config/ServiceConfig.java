package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.ProjectServiceImp;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.service.StoryServiceImp;
import se.majp.caseManagement.service.UserService;
import se.majp.caseManagement.service.UserServiceImp;

@Configuration
@EnableJpaRepositories("se.majp.caseManagement.repository")
public class ServiceConfig
{
	@Bean
	public UserService userService()
	{
		return new UserServiceImp();
	}

	@Bean
	public ProjectService projectService()
	{
		return new ProjectServiceImp();
	}
	
	@Bean
	public StoryService storyService()
	{
		return new StoryServiceImp();
	}
}
