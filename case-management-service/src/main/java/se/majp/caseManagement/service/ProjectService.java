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
	ProjectRepository projectRepository;
	
	IdGenerator generator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();
	
	public Project addProject(User user, Project project)
	{
		if(userIsOwner(user, project))
		{
			Project projectToSave = new Project(generator.getNextId(), project.getName(), project.getDescription());
			projectToSave.getTeam().addUser(user, Role.OWNER);
			return projectRepository.save(projectToSave);
		}
		
		throw new PermissionDeniedException("Permission denied");
	}
	
	public Project removeProject(User user, Project project)
	{
		if(userIsOwner(user, project))
		{
			projectRepository.delete(project);
			return project;
		}
		
		throw new PermissionDeniedException("Permission denied");
	}
	
	public Project updateProject(Project project)
	{
		return projectRepository.save(project);
	}
	
	public Project addOrUpdateTeamMember(User user, Role role, Project project)
	{
		if(user.getUserId() == null) 
		{
			throw new IllegalArgumentException("User not saved in DB");
		}
		
		project.getTeam().addUser(user, role);
		return projectRepository.save(project);
	}
	
	public Project removeTeamMember(User user, Project project)
	{
		project.getTeam().removeUser(user);
		return projectRepository.save(project);
	}

	private boolean userIsOwner(User user, Project project)
	{
		return project.getTeam().getRole(user).equals(Role.OWNER);
	}
	
}
