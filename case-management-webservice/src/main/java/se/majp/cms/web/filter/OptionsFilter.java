package se.majp.cms.web.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class OptionsFilter implements ContainerRequestFilter
{
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		if(requestContext.getMethod().equals("OPTIONS")) 
		{
			requestContext.abortWith(Response.ok().build());
		}
	}
}
