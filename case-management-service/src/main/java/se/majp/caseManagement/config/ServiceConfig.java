package se.majp.caseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.majp.caseManagement.service.UserService;

@Configuration
public class ServiceConfig
{
	@Bean
	public UserService userService()
	{
		return new UserService();
	}

}
