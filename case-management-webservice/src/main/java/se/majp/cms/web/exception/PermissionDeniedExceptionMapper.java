package se.majp.cms.web.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

import se.majp.cms.exception.PermissionDeniedException;

@Provider
public class PermissionDeniedExceptionMapper implements ExceptionMapper<PermissionDeniedException>
{
	private final Gson gson = new Gson();
	
	@Override
	public Response toResponse(PermissionDeniedException exception)
	{
		return Response.status(Status.UNAUTHORIZED)
					   .entity(gson.toJson(exception.getMessage()))
					   .type(MediaType.APPLICATION_JSON)
					   .build();
	}
}
