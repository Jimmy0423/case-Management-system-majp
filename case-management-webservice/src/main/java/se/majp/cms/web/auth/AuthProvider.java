package se.majp.cms.web.auth;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import se.majp.cms.exception.AuthorizationException;

public class AuthProvider
{	
	private static final Map<String, String> tokens = new HashMap<>();
	
	public String generateToken(String userId)
	{	
		Random random = new SecureRandom();
	    String token = new BigInteger(130, random).toString(32);
	    tokens.put(token, userId);
	    
	    return token;
	}

	public boolean hasToken(String token)
	{
		return tokens.containsKey(token);
	}

	public String getUserIdFromToken(String token)
	{
		if(tokens.containsKey(token))
		{
			return tokens.get(token);
		}
		
		throw new AuthorizationException("User not logged in");
	}
}
