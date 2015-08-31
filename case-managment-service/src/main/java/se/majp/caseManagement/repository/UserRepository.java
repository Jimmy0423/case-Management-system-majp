package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	List<User> findByEmail(String email);

	List<User> findByfirstName(String firstName);

	List<User> findByLastName(String lastName);

	List<User> findByEmailOrFirstNameOrLastName(String email, String firstName, String lastName);

	// No team in user
	//List<User> findByTeam(Team team);
}
