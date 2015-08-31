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
	@OneToMany
	@JoinTable(name = "member_in_team")
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

	public void addAll(List<TeamMember> members)
	{
		this.members.addAll(members);
	}

	public void removeUser(TeamMember member)
	{
		members.remove(member);
	}
}
