package se.majp.caseManagement.web.auth;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Authorize
public final class AuthorizationFilter implements ContainerRequestFilter
{
	@Context
	private UriInfo info;
	
	@Override
	public void filter(ContainerRequestContext context) throws IOException
	{
		final String token = context.getHeaderString("token");
		final AuthProvider provider = new AuthProvider();
		final String id = info.getPathParameters().get("userId").toString();
		
		if(provider.hasToken(token, id) == false)
		{
			context.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}
	}
}
