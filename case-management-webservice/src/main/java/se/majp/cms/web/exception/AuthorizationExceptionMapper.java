package se.majp.cms.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.majp.cms.exception.AuthorizationException;

@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<AuthorizationException>
{
	@Override
	public Response toResponse(AuthorizationException exception)
	{
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
	}

}
