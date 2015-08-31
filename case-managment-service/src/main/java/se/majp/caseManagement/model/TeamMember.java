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
	
	public enum Role
	{
		MEMBER, OWNER
	}
}
