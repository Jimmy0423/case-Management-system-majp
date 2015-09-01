package se.majp.caseManagement.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User extends AbstractEntity
{
	@Column(unique = true)
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER)
	Collection<Project> projects = new ArrayList<>();

	protected User(){}

	public User(String email, String firstName, String lastName, String password)
	{
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPassword()
	{
		return password;
	}
	
	public User addProject(Project project)
	{
		if (projects.contains(project))
		{
			throw new IllegalArgumentException("Project already exists in user");
		}
		
		projects.add(project);
		return this;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result *= prime + password.hashCode();
		result *= prime + email.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof User)
		{
			User other = (User) obj;
			return password.equals(other.getPassword()) && email.equals(other.getEmail());
		}

		return false;
	}
}
