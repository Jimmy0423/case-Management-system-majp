package se.majp.cms.service;

import java.util.List;

import se.majp.cms.model.Project;
import se.majp.cms.model.TeamMember;

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
