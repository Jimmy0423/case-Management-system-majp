package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_team_member")
public class TeamMember extends AbstractEntity
{
	@OneToOne
	private User user;
	
	private Role role;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_team_member_story", joinColumns = @JoinColumn(name = "team_member_id"), 
										inverseJoinColumns = @JoinColumn(name = "story_id"))
	private Collection<Story> stories = new ArrayList<>();
	
	@OneToOne
	private Team team;
	
	protected TeamMember(){}

	public TeamMember(User user, Role role, Team team)
	{
		this.user = user;
		this.role = role;
		this.team = team;
	}
	
	public User getUser()
	{
		return user;
	}

	public Role getRole()
	{
		return role;
	}

	public Collection<Story> getStories()
	{
		return stories;
	}

	public Team getTeam()
	{
		return team;
	}

	public TeamMember addStory(Story story)
	{
		if(stories.contains(story))
		{
			throw new IllegalArgumentException("Story already added");
		}
		
		stories.add(story);
		return this;
	}
	
}
