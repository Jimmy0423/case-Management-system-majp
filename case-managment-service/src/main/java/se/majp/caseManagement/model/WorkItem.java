package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_work_item")
public class WorkItem extends AbstractEntity {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "workItemId")
	private int workItemId;

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

	public WorkItem(int workItemId, String description, Priority priority, Status status) {
		this(workItemId, description, priority, status, null);

	}

	public WorkItem(int workItemId, String description, Priority priority, Status status, TeamMember teamMember) {
		this.workItemId = workItemId;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.teamMember = teamMember;
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
