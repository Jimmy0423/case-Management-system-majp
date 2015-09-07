package se.majp.caseManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.exception.PermissionDeniedException;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.util.IdGenerator;

public class ProjectService
{
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	StoryRepository storyRepository;

	IdGenerator idGenerator = IdGenerator.getBuilder().length(8).characters('0', 'z').build();

	public Project addProject(User user, Project project)
	{
		Project projectToSave = new Project(idGenerator.getNextId(), project.getName(), project.getDescription());
		projectToSave.getTeam().addUser(user, Role.OWNER);
		return projectRepository.save(projectToSave);
	}
	
	public Project findByProjectId(String projectId)
	{
		List<Project> projects = projectRepository.findByProjectId(projectId);
		
		if(projects.size() == 0)
		{
			throw new IllegalArgumentException("Project not found in DB");
		}
		
		return projects.get(0);
	}

	public Project updateProject(Project project)
	{
		return projectRepository.save(project);
	}

	public Project addOrUpdateTeamMember(User user, Role role, Project project)
	{
		if (user.getUserId() == null)
		{
			throw new IllegalArgumentException("User not saved in DB");
		}

		project.getTeam().addUser(user, role);
		return projectRepository.save(project);
	}

	public Project removeTeamMember(User user, Project project)
	{
		if(project.getProjectId() != null)
		{
			project.getTeam().removeUser(user);
			return projectRepository.save(project);
		}
		
		throw new IllegalArgumentException("Project not in DB");
	}

	public Project addStoryToBacklog(Project project, Story story)
	{
		Story storyToSave = new Story(idGenerator.getNextId(), story.getName(), story.getDescription(), project, story.getStatus(), story.getPriority());
		storyRepository.save(storyToSave);

		return findByProjectId(project.getProjectId());
	}

	public Project removeStoryFromBacklog(Project project, Story story)
	{
		storyRepository.delete(story);

		return findByProjectId(project.getProjectId());
	}
	
	public Project removeProject(User user, Project project)
	{
		if (userIsOwner(user, project))
		{
			projectRepository.delete(project);
			return project;
		}

		throw new PermissionDeniedException("Permission denied");
	}

	private boolean userIsOwner(User user, Project project)
	{
		return project.getTeam().getRole(user).equals(Role.OWNER);
	}

}
