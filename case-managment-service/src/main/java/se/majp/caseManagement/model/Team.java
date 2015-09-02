package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Team
{
	@OneToMany
	private Collection<TeamMember> teamMembers = new ArrayList<>();
	
	protected Team(){}
	
	public Collection<TeamMember> getTeamMembers()
	{
		return teamMembers;
	}
	
	public Team addTeamMember(TeamMember teamMember)
	{
		if (teamMembers.contains(teamMember))
		{
			throw new IllegalArgumentException("Team member already in team");
		}
		
		teamMembers.add(teamMember);
		return this;
	}

	public void addAll(List<TeamMember> teamMembers)
	{
		this.teamMembers.addAll(teamMembers);
	}

	public void removeTeamMember(TeamMember teamMember)
	{
		this.teamMembers.remove(teamMember);
	}
}
