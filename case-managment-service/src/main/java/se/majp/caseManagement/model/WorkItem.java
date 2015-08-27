package se.majp.caseManagement.model;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_work_item")
public class WorkItem {

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

	@ManyToMany
	private Collection<User> users;

	@OneToMany
	private Collection<Issue> issues;

	@ManyToMany
	private Project project;

	protected WorkItem() {}

	public WorkItem(int workItemId, String description, Priority priority, Status status) {

		this.workItemId = workItemId;
		this.description = description;
		this.priority = priority;
		this.status = status;
	}

	enum Priority {
		VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW
	}

	enum Status {
		// add status
		// example
		INPROGRESS, DONE, TEST
	}

}
