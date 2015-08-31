package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Team extends AbstractEntity
{
	private final Collection<TeamMember> members = new ArrayList<>();

	protected Team(){}

	public Team addUser(TeamMember member)
	{
		if (members.contains(member))
		{
			throw new IllegalArgumentException("User already in team");
		}
		
		members.add(member);
		return this;
	}

	public Team addAll(List<TeamMember> members)
	{
		this.members.addAll(members);
		return this;
	}

	public Team removeUser(User user)
	{
		members.remove(user);
		return this;
	}
}
