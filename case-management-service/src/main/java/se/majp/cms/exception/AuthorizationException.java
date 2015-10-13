package se.majp.cms.exception;

public class AuthorizationException extends RuntimeException
{
	private static final long serialVersionUID = 8220587625519880116L;
	
	public AuthorizationException(String message, Throwable throwable)
	{
		super(message);
	}
	
	public AuthorizationException(String message)
	{
		super(message);
	}
	
	public AuthorizationException()
	{
		super();
	}

}
