package se.majp.cms.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import se.majp.cms.model.User;

public class SpringSecurityAuditorAware implements AuditorAware<String>
{

	@Override
	public String getCurrentAuditor()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated())
		{
			return null;
		}
		
		return ((User) authentication.getPrincipal()).getEmail();
	}

}
