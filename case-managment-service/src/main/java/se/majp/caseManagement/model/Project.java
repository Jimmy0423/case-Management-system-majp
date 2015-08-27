package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_project")
public class Project extends AbstractEntity
{
	private Team team;

	private Collection<WorkItem> workItems;

	private String name;
	private String description;
	private String projectId;

	protected Project(){}

	public Project(String name, String description)
	{
		this.name = name;
		this.description = description;
		team = new Team();
		workItems = new ArrayList<>();
		// TODO Generate projectId
	}

	public Team getTeam()
	{
		return team;
	}

	public Collection<WorkItem> getWorkItems()
	{
		return workItems;
	}

	public void addWorkItem(WorkItem workItem)
	{
		if (!workItems.contains(workItem))
		{
			workItems.add(workItem);			
		}
		else
		{
			throw new IllegalArgumentException("WorkItems already contains that workItem");
		}
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}
	
	public String getProjectId()
	{
		return projectId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (Project.class == obj.getClass()) 
		{
			Project other = (Project) obj;
			
			return (this.name == other.name && this.description == other.description 
				   && this.projectId == other.projectId);
		}
		
		return false;
	}

	
	
}
