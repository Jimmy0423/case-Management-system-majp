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

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Collection<Story> getStories()
	{
		return stories;
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

	public TeamMember removeStory(Story story)
	{
		stories.remove(story);
		return this;
	}
}
