package se.majp.caseManagement.web.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class AuthProvider
{
	private static final Map<String, String> tokens = new HashMap<>();

	public String addToken(String userId)
	{
		final String token = getToken();
		tokens.put(token, userId);
		
		return token;
	}

	public boolean hasToken(String token, String userId)
	{
		if(tokens.get(token) == null)
		{
			return false;
		}
		else if(tokens.get(token).equals(userId))
		{
			return true;
		}
		
		return false;
	}
	
	public void removeToken(String token)
	{
		if(tokens.containsKey(token))
		{
			tokens.remove(token);
		}
	}
	
	private String getToken()
	{
		return UUID.randomUUID().toString();
	}
}
