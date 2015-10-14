package se.majp.cms.web.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

import se.majp.cms.exception.AuthorizationException;

@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<AuthorizationException>
{
	private final Gson gson = new Gson();
	
	@Override
	public Response toResponse(AuthorizationException exception)
	{
		return Response.status(Status.UNAUTHORIZED)
						.entity(gson.toJson(exception.getMessage()))
						.type(MediaType.APPLICATION_JSON)
						.build();
	}

}
