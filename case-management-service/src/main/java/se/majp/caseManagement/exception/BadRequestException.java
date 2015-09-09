package se.majp.caseManagement.exception;

public class BadRequestException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
	public BadRequestException(String message)
	{
		super(message);
	}
	
	public BadRequestException()
	{
		super();
	}
}
