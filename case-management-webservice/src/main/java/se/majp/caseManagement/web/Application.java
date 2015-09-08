package se.majp.caseManagement.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.RequestContextFilter;

import se.majp.caseManagement.web.service.StoryService;

public class Application extends ResourceConfig
{
	public Application()
	{
		register(RequestContextFilter.class);
		register(StoryService.class);
	}
}
