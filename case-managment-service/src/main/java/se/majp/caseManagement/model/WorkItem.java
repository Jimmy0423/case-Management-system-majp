package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_work_item")
public class WorkItem extends AbstractEntity
{
	private String workItemId;
	private String description;
	private Priority priority;
	private Status status;

	@ManyToOne
	private TeamMember teamMember;

	@OneToMany
	@JoinTable(name = "tbl_workitem_issue")
	private Collection<Issue> issues;

	@ManyToOne
	private Project project;

	protected WorkItem(){}

	public WorkItem(String workItemId, String description, Priority priority, Status status, TeamMember teamMember)
	{
		this.workItemId = workItemId;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.teamMember = teamMember;
	}
	
	public WorkItem(String workItemId, String description, Priority priority, Status status)
	{
		this(workItemId, description, priority, status, null);
	}

	public String getDescription()
	{
		return description;
	}

	public String getWorkItemId()
	{
		return workItemId;
	}

	public Priority getPriority()
	{
		return priority;
	}

	public Status getStatus()
	{
		return status;
	}

	public TeamMember getTeamMember()
	{
		return teamMember;
	}

	public Collection<Issue> getIssues()
	{
		return issues;
	}

	public Project getProject()
	{
		return project;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result *= prime + description.hashCode();
		result *= prime + workItemId.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof WorkItem)
		{
			WorkItem other = (WorkItem) obj;
			return description.equals(other.getDescription()) && workItemId.equals(other.getWorkItemId());
		}

		return false;
	}

	public enum Priority
	{
		VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW
	}

	public enum Status
	{
		// add status
		// example
		INPROGRESS, DONE, TEST
	}

}
