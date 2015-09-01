package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.TeamMember;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long>
{

}
