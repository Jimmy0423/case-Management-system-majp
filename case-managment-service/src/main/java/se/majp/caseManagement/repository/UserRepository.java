package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{

}
