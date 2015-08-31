package se.majp.caseManagement.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_issue")
public class Issue extends AbstractEntity
{
	private String issueId;
	private String description;

	@ManyToOne
	private TeamMember teamMember;

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

	public TeamMember getTeamMember()
	{
		return teamMember;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result *= prime + issueId.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Issue)
		{
			Issue other = (Issue) obj;
			return issueId.equals(other.getIssueId());
		}

		return false;
	}
}
