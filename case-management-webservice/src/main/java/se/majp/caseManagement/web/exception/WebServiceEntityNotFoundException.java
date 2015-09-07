package se.majp.caseManagement.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.majp.caseManagement.exception.EntityNotFoundException;

@Provider
public class WebServiceEntityNotFoundException implements ExceptionMapper<EntityNotFoundException>
{
	@Override
	public Response toResponse(EntityNotFoundException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
