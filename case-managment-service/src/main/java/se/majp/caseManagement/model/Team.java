package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Team extends AbstractEntity
{
	private String teamId;
	
	@OneToMany
	@JoinTable(name = "member_in_team")
	private final Collection<TeamMember> members = new ArrayList<>();

	protected Team(){}
	
	public Team(String teamId)
	{
		this.teamId = teamId;
	}
	
	public String getTeamId()
	{
		return teamId;
	}
	
	public Collection<TeamMember> getMembers()
	{
		return members;
	}

	public Team addUser(TeamMember member)
	{
		if (members.contains(member))
		{
			throw new IllegalArgumentException("User already in team");
		}
		
		members.add(member);
		return this;
	}

	public void addAll(List<TeamMember> members)
	{
		this.members.addAll(members);
	}

	public void removeUser(TeamMember member)
	{
		members.remove(member);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result += prime * teamId.hashCode();
		result += prime * members.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Team)
		{
			Team other = (Team) obj;
			return other.getTeamId().equals(getTeamId()) && other.getMembers().equals(getMembers());
		}
		
		return false;
	}
}
