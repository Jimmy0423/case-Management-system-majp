package se.majp.caseManagement.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AuthorizationException extends WebApplicationException
{
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String message)
	{
		super(Response.status(Status.UNAUTHORIZED)
					  .entity(message)
					  .build());
	}
	
	public AuthorizationException()
	{
		super(Response.status(Status.UNAUTHORIZED)
				  	  .build());
	}
}
