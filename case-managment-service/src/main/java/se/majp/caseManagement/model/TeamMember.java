package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TeamMember extends AbstractEntity
{
	@OneToOne
	private User user;

	private Role role;
	
	@OneToMany(mappedBy = "teamMember")
	private Collection<WorkItem> workItems;
	
	protected TeamMember(){}
	
	public TeamMember(User user, Role role)
	{
		this.user = user;
		this.role = role;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public Role getRole()
	{
		return role;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result += prime * user.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof TeamMember)
		{
			TeamMember other = (TeamMember) obj;
			
			return other.getUser().equals(getUser());
		}
		
		return false;
	}
	
	public enum Role
	{
		MEMBER, OWNER
	}
}
