package se.majp.cms.web.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.cms.exception.AuthorizationException;
import se.majp.cms.service.UserService;
import se.majp.cms.web.auth.AuthProvider;
import se.majp.cms.web.auth.Authorize;

@Provider
@Authorize
public class AuthFilter implements ContainerRequestFilter
{
	@Autowired
	private UserService userService;

	@Context
	private UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String projectId = uriInfo.getPathParameters().get("projectId").get(0);
		AuthProvider provider = new AuthProvider();

		if (token == null)
		{
			throw new AuthorizationException("User not signed in");
		}

		if (provider.hasToken(token))
		{
			String userId = provider.getUserIdFromToken(token);

			if (userService.isMemberOfProject(userId, projectId))
			{
				return;
			}
		}

		requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
	}

}
