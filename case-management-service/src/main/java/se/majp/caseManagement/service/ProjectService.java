package se.majp.caseManagement.service;

import java.util.List;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;

public interface ProjectService
{
	Project addOrUpdateProject(User user, Project project);

	Project findByProjectId(String projectId);

	List<Project> findAllProjectsByUser(String userId);

	Project addOrUpdateTeamMember(User user, Role role, Project project);

	Project removeTeamMember(User user, Project project);

	void removeProject(User user, Project project);
}
