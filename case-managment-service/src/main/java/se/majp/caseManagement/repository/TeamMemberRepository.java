package se.majp.caseManagement.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.TeamMember;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long>
{
	@Query("select p.team.teamMembers from Project p where p = ?1")
	Collection<TeamMember> findByProject(Project project);
}
