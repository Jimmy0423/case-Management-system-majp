package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_team")
public class Team extends AbstractEntity
{
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_team_members_in_team")
	private Collection<TeamMember> teamMembers = new ArrayList<>();
	
	@OneToOne
	private Project project;
	
	protected Team(){}
	
	public Team(Project project)
	{
		this.project = project;
	}
	
	public Collection<TeamMember> getTeamMembers()
	{
		return teamMembers;
	}
	
	public Project getProject()
	{
		return project;
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
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result += prime * project.hashCode();
		result += prime * teamMembers.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Team)
		{
			Team other = (Team) obj;
			return project.equals(other.getProject()) && teamMembers.equals(other.getTeamMembers());
		}
		
		return false;
	}
}
