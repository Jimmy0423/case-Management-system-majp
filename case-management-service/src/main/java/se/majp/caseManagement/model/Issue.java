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
	private Story story;

	protected Issue(){}

	public Issue(String title, String description, Story story)
	{
		this.title = title;
		this.description = description;
		this.story = story;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public Story getStory()
	{
		return story;
	}
}
