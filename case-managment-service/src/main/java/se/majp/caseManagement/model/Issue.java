package se.majp.caseManagement.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_issue")
public class Issue extends AbstractEntity
{
	private String issueId;
	private String description;
	// Relation
	private User user;

	public Issue(String issueId, String description)
	{
		this.issueId = issueId;
		this.description = description;
	}

	public String getIssueId()
	{
		return issueId;
	}

	public String getDescription()
	{
		return description;
	}

	public User getUser()
	{
		return user;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((issueId == null) ? 0 : issueId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Issue other = (Issue) obj;
		if (issueId == null)
		{
			if (other.issueId != null) return false;
		}
		else if (!issueId.equals(other.issueId)) return false;
		return true;
	}

}
