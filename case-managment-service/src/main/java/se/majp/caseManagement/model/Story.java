package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_story")
public class Story extends AbstractEntity
{
	private String storyId;
	private String description;
	private Priority priority;
	private Status status;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_story_issue")
	private Collection<Issue> issues = new ArrayList<>();

	protected Story(){}

	public Story(String storyId, String description, Priority priority, Status status)
	{
		this.storyId = storyId;
		this.description = description;
		this.priority = priority;
		this.status = status;
	}

	public String getDescription()
	{
		return description;
	}

	public String getStoryId()
	{
		return storyId;
	}

	public Priority getPriority()
	{
		return priority;
	}

	public Status getStatus()
	{
		return status;
	}

	public Collection<Issue> getIssues()
	{
		return issues;
	}
	
	public Story addIssue(Issue issue)
	{
		issues.add(issue);
		return this;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result *= prime + description.hashCode();
		result *= prime + storyId.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Story)
		{
			Story other = (Story) obj;
			return description.equals(other.getDescription()) && storyId.equals(other.getStoryId());
		}

		return false;
	}

	public enum Status
	{
		PENDING, INPROGRESS, DONE, ISSUED
	}

}
