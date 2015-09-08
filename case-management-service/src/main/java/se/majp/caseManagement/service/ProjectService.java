package se.majp.caseManagement.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.PermissionDeniedException;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.util.IdGenerator;

public class ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;

	private final IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	public Project addOrUpdateProject(User user, Project project)
	{
		if(project.getProjectId() == null)
		{
			project = new Project(idGenerator.getNextId(), project.getName(), project.getDescription());
			project.getTeam().addUser(user, Role.OWNER);
		}
		
		return projectRepository.save(project);
	}

	public Project findByProjectId(String projectId)
	{
		Project project = projectRepository.findByProjectId(projectId);
		
		if (project == null)
		{
			throw new EntityNotFoundException("Project not found in DB");
		}

		return project; 
	}
	
	public List<Project> findAllProjectsByUser(String userId)
	{
		User user = userRepository.findByUserId(userId);
		
		if(user == null)
		{
			throw new EntityNotFoundException("User not found with a userId: " + userId);
		}
		
		return projectRepository.findAllProjectsForUser(user);
	}

	public Project addOrUpdateTeamMember(User user, Role role, Project project)
	{
		if (user.getUserId() == null)
		{
			throw new EntityNotFoundException("User not saved in DB");
		}

		project.getTeam().addUser(user, role);
		return projectRepository.save(project);
	}

	public Project removeTeamMember(User user, Project project)
	{
		if (project.getProjectId() != null)
		{
			project.getTeam().removeUser(user);
			return projectRepository.save(project);
		}

		throw new EntityNotFoundException("Project not in DB");
	}

	public void removeProject(User user, Project project)
	{
		if (project.getTeam().userHasRole(user, Role.OWNER))
		{
			projectRepository.delete(project);
		}

		throw new PermissionDeniedException("Permission denied");
	}
}
