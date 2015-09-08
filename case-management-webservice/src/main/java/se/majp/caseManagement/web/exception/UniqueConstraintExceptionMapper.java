package se.majp.caseManagement.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.majp.caseManagement.exception.UniqueConstraintException;

@Provider
public class UniqueConstraintExceptionMapper implements ExceptionMapper<UniqueConstraintException>
{
	@Override
	public Response toResponse(UniqueConstraintException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
