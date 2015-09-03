package se.majp.caseManagement.exception;

public class PermissionDeniedException extends RuntimeException
{
	private static final long serialVersionUID = 568821540135276832L;

	public PermissionDeniedException(String string)
	{
		super(string);
	}
}
