package se.majp.cms.web.auth;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import se.majp.cms.exception.AuthorizationException;

@Provider
@SecureUsers
public final class UsersFilter implements ContainerRequestFilter
{
	@Context
	private UriInfo uriInfo;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		AuthProvider provider = new AuthProvider();
		
		if(token == null)
		{
			throw new AuthorizationException();
		}
		
		if(uriInfo.getPathParameters().containsKey("userId"))
		{
			String userId = uriInfo.getPathParameters().getFirst("userId");
			
			if(provider.hasToken(token, userId))
			{
				return;
			}
		}
		
		if (uriInfo.getPathParameters().containsKey("token"))
		{
			String pathToken = uriInfo.getPathParameters().getFirst("token");
			
			if (provider.hasToken(pathToken) && pathToken.equals(token));
			{
				return;
			}
		}
		
		throw new AuthorizationException();
	}
}
