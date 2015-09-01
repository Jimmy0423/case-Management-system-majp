package se.majp.caseManagement.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_issue")
public class Issue extends AbstractEntity
{
	private String title;
	private String description;

	@ManyToOne
	private User user;
	
	protected Issue(){}

	public Issue(String title, String description, User user)
	{
		this.title = title;
		this.description = description;
		this.user = user;
	}

	public String getTitle()
	{
		return title;
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
		result *= prime + title.hashCode();

		return result;
	}

//	@Override
//	public boolean equals(Object obj)
//	{
//		if (obj instanceof Issue)
//		{
//			Issue other = (Issue) obj;
//			return title.equals(other.getIssueId());
//		}
//
//		return false;
//	}
}
