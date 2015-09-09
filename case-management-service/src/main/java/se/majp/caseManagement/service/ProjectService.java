package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;

public interface ProjectService
{
	Project addOrUpdateProject(Project project);

	Project findByProjectId(String projectId);

	List<Project> findAllProjectsByUser(String userId);
	
	List<Project> findAllProjects();

	Project addOrUpdateTeamMember(String projectId, String userId, Role role);

	Project removeTeamMember(String projectId, User user);

	void removeProject(Project project);
}
