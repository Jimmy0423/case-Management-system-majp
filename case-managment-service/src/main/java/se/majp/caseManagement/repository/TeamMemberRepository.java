package se.majp.caseManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.model.TeamMember.Role;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long>
{
	List<TeamMember> finByRole(Role role);
}
