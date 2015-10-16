package se.majp.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.cms.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	@Query("select u from User u where firstName = ?1 or lastName = ?1 or email = ?1")
	List<User> findByFirstNameOrLastNameOrEmail(String value);

	User findByUserId(String userId);

	@Query("select indices(p.team.users) from Project p where p.projectId = ?1")
	List<User> findByProject(String projectId);

	User findByEmail(String email);
	
	@Query("select u from User u where firstName like %?1% or lastName like %?1% or email like %?1%")
	List<User> searchByNameOrEmail(String value);
}
