package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.majp.caseManagement.service.ProjectServiceImp;
import se.majp.caseManagement.service.StoryServiceImp;
import se.majp.caseManagement.service.UserServiceImp;

@Configuration
@EnableJpaRepositories("se.majp.caseManagement.repository")
public class ServiceConfig
{
	@Bean
	public UserServiceImp userService()
	{
		return new UserServiceImp();
	}

	@Bean
	public ProjectServiceImp projectService()
	{
		return new ProjectServiceImp();
	}
	
	@Bean
	public StoryServiceImp storyService()
	{
		return new StoryServiceImp();
	}
}
