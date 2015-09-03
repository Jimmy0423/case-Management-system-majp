package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.majp.caseManagement.service.TeamMemberService;

@Configuration
public class ServiceConfig
{
	@Bean
	public TeamMemberService teamMemberService()
	{
		return new TeamMemberService();
	}

}
