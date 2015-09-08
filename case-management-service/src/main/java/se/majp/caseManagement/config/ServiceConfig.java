package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.majp.caseManagement.service.ProjectService;
import se.majp.caseManagement.service.StoryService;
import se.majp.caseManagement.service.UserService;

@Configuration
@EnableJpaRepositories("se.majp.caseManagement.repository")
public class ServiceConfig
{
	@Bean
	@Scope("request")
	public UserService userService()
	{
		return new UserService();
	}

	@Bean
	@Scope("request")
	public ProjectService projectService()
	{
		return new ProjectService();
	}
	
	@Bean
	@Scope("request")
	public StoryService storyService()
	{
		return new StoryService();
	}
}
