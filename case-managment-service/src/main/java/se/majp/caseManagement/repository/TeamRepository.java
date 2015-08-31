package se.majp.caseManagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.majp.caseManagement.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>
{

}
