package se.majp.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.majp.cms.service.IssueService;
import se.majp.cms.service.IssueServiceImpl;
import se.majp.cms.service.ProjectService;
import se.majp.cms.service.ProjectServiceImpl;
import se.majp.cms.service.StoryService;
import se.majp.cms.service.StoryServiceImpl;
import se.majp.cms.service.UserService;
import se.majp.cms.service.UserServiceImpl;

@Configuration
@EnableJpaRepositories("se.majp.cms.repository")
@EnableJpaAuditing
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
	
//	@Bean
//	public AuditorAware<String> createAuditorProvider()
//	{
//		return new SecurityProvider();
//	}
	
	@Bean
	public AuditingEntityListener createAuditingListener()
	{
		return new AuditingEntityListener();
	}
	
	public static class SecurityAuditor implements AuditorAware<String>
	{

		@Override
		public String getCurrentAuditor()
		{
			
			return null;
		}
		
	}
}
