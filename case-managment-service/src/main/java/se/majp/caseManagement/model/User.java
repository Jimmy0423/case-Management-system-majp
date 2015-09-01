package se.majp.caseManagement.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToMany(mappedBy = "users")
	private Collection<Team> teams;
	
	@OneToMany
	@JoinTable(name = "tbl_user_story")
	private Collection<Story> stories;

	protected User()
	{
	}

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

	public Collection<Team> getTeams()
	{
		return teams;
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
