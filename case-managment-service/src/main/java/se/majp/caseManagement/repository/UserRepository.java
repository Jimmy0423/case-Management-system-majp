package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	List<User> findByUserID(String userId);

	List<User> findByTeam(Team team);

}
