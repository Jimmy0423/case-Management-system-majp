package se.majp.caseManagement.model;

import java.util.Collection;

public class TeamMember extends AbstractEntity
{
	private final User user;
	private final Role role;
	private Team team;
	private Collection<WorkItem> workItems;
	
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
	
	public enum Role
	{
		MEMBER, OWNER
	}
}
