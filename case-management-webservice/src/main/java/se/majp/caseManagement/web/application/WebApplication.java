package se.majp.caseManagement.web.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import se.majp.caseManagement.web.service.StoryService;

public class WebApplication extends ResourceConfig
{
	public WebApplication()
	{
		register(RequestContextFilter.class);
		register(StoryService.class);
	}

}