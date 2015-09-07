package se.majp.caseManagement.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.majp.caseManagement.exception.PermissionDeniedException;

@Provider
public class WebServicePermissionDeniedException implements ExceptionMapper<PermissionDeniedException>
{
	@Override
	public Response toResponse(PermissionDeniedException exception)
	{
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
	}
}
