package se.majp.caseManagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_project")
public class Project extends AbstractEntity
{
	@Column(name = "project_id")
	private String projectId;
	private String name;
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Team team;

	protected Project(){}

	public Project(String projectId, String name, String description)
	{
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		team = new Team(this);
	}

	public Team getTeam()
	{
		return team;
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
		result *= prime + description.hashCode();
		result *= prime + name.hashCode();
		result *= prime + projectId.hashCode();
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Project) 
		{
			Project other = (Project) obj;
			
			return (name.equals(other.getName()) && description.equals(other.getDescription()) &&
					projectId.equals(other.getProjectId()));
		}
		
		return false;
	}
}
