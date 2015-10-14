package se.majp.cms.web.auth;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.cms.exception.AuthorizationException;
import se.majp.cms.model.Story;
import se.majp.cms.service.StoryService;
import se.majp.cms.service.UserService;

@Provider
@SecureStories
public class StoriesFilter implements ContainerRequestFilter
{
	@Autowired
	private StoryService storyService;
	
	@Autowired 
	private UserService userService;

	@Context
	private UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		AuthProvider provider = new AuthProvider();
		
		if (token == null)
		{
			throw new AuthorizationException("User not signed in");
		}
		
		if (provider.hasToken(token))
		{
			String userId = provider.getUserIdFromToken(token);
			
			if (uriInfo.getPathParameters().containsKey("storyId"))
			{
				String storyId = uriInfo.getPathParameters().getFirst("storyId");
				Story story = storyService.findStoryById(storyId);
				String projectId = story.getProject().getProjectId();
				
				if (userService.isMemberOfProject(userId, projectId))
				{
					return;
				}
			}
		}
		
		throw new AuthorizationException();
	}

}
