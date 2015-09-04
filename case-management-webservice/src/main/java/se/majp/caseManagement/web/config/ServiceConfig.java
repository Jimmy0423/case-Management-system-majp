package se.majp.caseManagement.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import se.majp.caseManagement.service.UserService;

@Configuration
@ComponentScan
public class ServiceConfig
{
	@Bean
	public UserService getUserService()
	{
		return new UserService();
	}
}