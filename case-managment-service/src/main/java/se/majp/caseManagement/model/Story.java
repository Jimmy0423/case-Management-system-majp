package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_story")
public class Story extends AbstractEntity
{
	private String name;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	private Project project;
	
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "story", fetch = FetchType.EAGER)
	private Collection<Issue> issues;

	protected Story(){}

	public Story(String name, String description, Project project, Status status)
	{
		this.name = name;
		this.description = description;
		this.project = project;
		this.status = status;
		this.issues = new ArrayList<>();
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Status getStatus()
	{
		return status;
	}
	
	public Project getProject()
	{
		return project;
	}

	public Collection<Issue> getIssues()
	{
		return issues;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

	public Story addIssue(Issue issue)
	{
		issues.add(issue);
		return this;
	}
}
