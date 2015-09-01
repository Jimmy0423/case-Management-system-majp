package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_team")
public class Team extends AbstractEntity
{
	private String teamId;
	
	@OneToMany
	@JoinTable(name = "member_in_team")
	private final Collection<User> users = new ArrayList<>();

	protected Team(){}
	
	public Team(String teamId)
	{
		this.teamId = teamId;
	}
	
	public String getTeamId()
	{
		return teamId;
	}
	
	public Collection<User> getMembers()
	{
		return users;
	}

	public Team addUser(User user)
	{
		if (users.contains(user))
		{
			throw new IllegalArgumentException("User already in team");
		}
		
		users.add(user);
		return this;
	}

	public void addAll(List<User> users)
	{
		this.users.addAll(users);
	}

	public void removeUser(User user)
	{
		users.remove(user);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result += prime * teamId.hashCode();
		result += prime * users.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Team)
		{
			Team other = (Team) obj;
			return teamId.equals(other.getTeamId()) && users.equals(other.getMembers());
		}
		
		return false;
	}
}
