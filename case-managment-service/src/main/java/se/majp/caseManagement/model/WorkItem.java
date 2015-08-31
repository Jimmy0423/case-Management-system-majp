package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_work_item")
public class WorkItem extends AbstractEntity {

	@Column(name = "workItemId")
	private String workItemId;

	@Column(name = "description")
	private String description;

	@Column(name = "priority")
	private Priority priority;

	@Column(name = "status")
	private Status status;

	@ManyToOne
	private TeamMember teamMember;

	@OneToMany
	private Collection<Issue> issues;

	@ManyToOne
	private Project project;

	protected WorkItem() {
	}

	public WorkItem(String workItemId, String description, Priority priority, Status status) {
		this(workItemId, description, priority, status, null);

	}

	public WorkItem(String workItemId, String description, Priority priority, Status status, TeamMember teamMember) {
		this.workItemId = workItemId;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.teamMember = teamMember;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((workItemId == null) ? 0 : workItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkItem other = (WorkItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (workItemId == null) {
			if (other.workItemId != null)
				return false;
		} else if (!workItemId.equals(other.workItemId))
			return false;
		return true;
	}

	public enum Priority {
		VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW
	}

	public enum Status {
		// add status
		// example
		INPROGRESS, DONE, TEST
	}

}
