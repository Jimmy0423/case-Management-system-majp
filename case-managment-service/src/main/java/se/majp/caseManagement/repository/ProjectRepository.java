package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>
{
	//Alla projekt som en viss user är med i teamet
	@Query("select p from Project p where ?1 in indices(p.team.users)")
	List<Project> findAllProjectsForUser(User user);
}
