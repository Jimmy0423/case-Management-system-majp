package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_team")
public class Team extends AbstractEntity
{
	@ManyToMany
	@JoinTable(name = "tbl_users_in_team")
	private Collection<User> users = new ArrayList<>();
	
	@OneToMany
	@JoinTable(name = "tbl_team_story", joinColumns = @JoinColumn(name = "team_id"), 
										inverseJoinColumns = @JoinColumn(name = "story_id"))
	private Collection<Story> stories = new ArrayList<>();
	
	@OneToOne
	private Project project;
	
	protected Team(){}
	
	public Team(Project project)
	{
		this.project = project;
	}
	
	public Collection<User> getUsers()
	{
		return users;
	}
	
	public Project getProject()
	{
		return project;
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
	
	public Team addStory(Story story)
	{
		if(stories.contains(story))
		{
			throw new IllegalArgumentException("Story already added");
		}
		
		stories.add(story);
		return this;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result += prime * project.hashCode();
		result += prime * users.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Team)
		{
			Team other = (Team) obj;
			return project.equals(other.getProject()) && users.equals(other.getUsers());
		}
		
		return false;
	}
}
