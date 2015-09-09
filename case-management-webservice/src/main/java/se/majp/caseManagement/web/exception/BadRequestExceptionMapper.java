package se.majp.caseManagement.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import se.majp.caseManagement.exception.BadRequestException;

public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException>
{
	@Override
	public Response toResponse(BadRequestException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
