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
import se.majp.cms.service.UserService;

@Provider
@SecureProjects
public class ProjectsFilter implements ContainerRequestFilter
{
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
			if (uriInfo.getPathParameters().containsKey("projectId"))
			{
				String projectId = uriInfo.getPathParameters().get("projectId").get(0);
				String userId = provider.getUserIdFromToken(token);

				if (userService.isMemberOfProject(userId, projectId))
				{
					return;
				}
			}
			else
			{
				return;
			}
		}

		throw new AuthorizationException("User not allowed on that url");
	}
}