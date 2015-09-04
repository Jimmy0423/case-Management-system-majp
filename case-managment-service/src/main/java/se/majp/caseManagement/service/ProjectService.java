package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.PermissionDeniedException;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.util.IdGenerator;

public class ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	private IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();
	
	public Project addProject(User user, Project project)
	{
		if(project.getProjectId() != null)
		{
			throw new IllegalArgumentException("project already added");
		}
		
		project.setProjectId(idGenerator.getNextId());
		project.getTeam().addUser(user, Role.OWNER);
		return projectRepository.save(project);
	}
	
	public Project removeProject(User user, Project project)
	{
		if(isOwner(user, project))
		{
			projectRepository.delete(project);
			return project;
		}
		
		throw new PermissionDeniedException("Only owners can remove projects");
	}
	
	public Project updateProject(User user, Project project)
	{
		if(project.getId() == null)
		{
			throw new IllegalArgumentException("Only owners can update project");
		}
		
		if(isOwner(user, project))
		{
			projectRepository.save(project);
			return project;
		}
		
		throw new PermissionDeniedException("User is not a owner");
	}
	
	public Project addTeamMember(User user, Role role, Project project)
	{
		project.getTeam().addUser(user, role);
		return project;
	}
	
	private boolean isOwner(User user, Project project)
	{
		return project.getTeam().getRole(user) != null && project.getTeam().getRole(user).equals(Role.OWNER);
	}
}
