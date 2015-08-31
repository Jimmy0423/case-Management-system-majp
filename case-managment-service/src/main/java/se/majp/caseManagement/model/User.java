package se.majp.caseManagement.model;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
	@ManyToMany
	private Collection<Project> projects;

	protected User()
	{
	}

	public User(String userId, String firstName, String lastName, String password)
	{
		this.email = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getUserId()
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

	public Collection<Project> getProjects()
	{
		return projects;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if (password == null)
		{
			if (other.password != null) return false;
		}
		else if (!password.equals(other.password)) return false;
		if (email == null)
		{
			if (other.email != null) return false;
		}
		else if (!email.equals(other.email)) return false;
		return true;
	}
}
