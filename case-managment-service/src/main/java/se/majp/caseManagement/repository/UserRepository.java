package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	List<User> findByUserId(String userId);

	List<User> findByfirstName(String firstName);

	List<User> findByLastName(String lastName);

	List<User> findByUserIdOrFirstNameOrLastName(String userId, String firstName, String lastName);

	List<User> findByTeam(Team team);

}
