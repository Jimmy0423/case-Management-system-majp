package se.majp.caseManagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.util.IdGenerator;

public class ProjectServiceImpl implements ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	private final IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	@Override
	public Project addOrUpdateProject(Project project)
	{
		if (project.getProjectId() == null)
		{
			project = new Project(idGenerator.getNextId(), project.getName(), project.getDescription());
		}

		return projectRepository.save(project);
	}

	@Override
	public Project findByProjectId(String projectId)
	{
		Project project = projectRepository.findByProjectId(projectId);

		if (project == null)
		{
			throw new EntityNotFoundException("Project not found in DB");
		}

		return project;
	}

	@Override
	public List<Project> findAllProjects()
	{
		List<Project> projects = new ArrayList<>();
		projectRepository.findAll().forEach(project -> {
			projects.add(project);
		});

		return projects;
	}

	@Override
	public List<Project> findAllProjectsByUser(String userId)
	{
		User user = userRepository.findByUserId(userId);

		if (user == null)
		{
			throw new EntityNotFoundException("User not found with a userId: " + userId);
		}

		return projectRepository.findAllProjectsForUser(user);
	}

	@Override
	public Project addOrUpdateTeamMember(String projectId, String userId, Role role)
	{
		Project project = projectRepository.findByProjectId(projectId);
		User user = userRepository.findByUserId(userId);
		
		if (user == null)
		{
			throw new EntityNotFoundException("User not saved in DB");
		}

		project.getTeam().addUser(user, role);
		return projectRepository.save(project);
	}

	@Override
	public Project removeTeamMember(String projectId, User user)
	{
		Project project = projectRepository.findByProjectId(projectId);
		
		if (project == null)
		{
			throw new EntityNotFoundException("Project not in DB");
		}

		project.getTeam().removeUser(user);
		return projectRepository.save(project);
	}

	@Override
	public void removeProject(Project project)
	{
		projectRepository.delete(project);
	}
}
