package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TeamMember extends AbstractEntity
{
	@OneToOne
	private User user;
	@Column(name = "role")
	private Role role;
	@OneToMany(mappedBy = "teamMember")
	@JoinColumn(name = "workItem_id")
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
	
	enum Role
	{
		MEMBER, OWNER
	}
}
