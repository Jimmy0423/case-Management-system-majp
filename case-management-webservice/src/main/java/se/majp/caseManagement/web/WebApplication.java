package se.majp.caseManagement.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
@Component
public class WebApplication extends ResourceConfig
{
	public WebApplication()
	{
		register(RequestContextFilter.class);
	}
}
