package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.TeamMember;

public interface ProjectService
{
	Project addOrUpdateProject(Project project);

	Project findByProjectId(String projectId);

	List<Project> findAllProjectsByUser(String userId);

	List<Project> findAllProjects();

	Project addOrUpdateTeamMember(String projectId, TeamMember teamMember);

	Project removeTeamMember(String projectId, String userId);

	void removeProject(String projectId);
}
