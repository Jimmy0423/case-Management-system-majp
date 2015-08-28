package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User extends AbstractEntity
{
	private String userId;
	private UserType userType;
	private String firstName;
	private String lastName;
	private String password;
	// relation
	private Collection<Project> projects;

	protected User()
	{
	}

	public User(String userId, UserType userType, String firstName, String lastName, String password)
	{
		this.userId = userId;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getUserId()
	{
		return userId;
	}

	public UserType getUserType()
	{
		return userType;
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
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (userId == null)
		{
			if (other.userId != null) return false;
		}
		else if (!userId.equals(other.userId)) return false;
		return true;
	}

	public enum UserType
	{
		OWNER, MEMBER
	}

}
