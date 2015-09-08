package se.majp.caseManagement.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import se.majp.caseManagement.service.UserService;

@Configuration
public class ServiceConfig
{
	@Bean
	@Scope("request")
	public UserService userService()
	{
		return new UserService();
	}
}
