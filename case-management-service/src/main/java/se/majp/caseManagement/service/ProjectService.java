package se.majp.caseManagement.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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

	public List<Story> findBacklog(String projectId)
	{
		if(projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findBacklogForProject(projectId);
		}
		
		throw new EntityNotFoundException("Project not in DB");
	}

	public List<Story> findAllStoriesInProject(String projectId)
	{
		if(projectRepository.findByProjectId(projectId) != null)
		{
			return storyRepository.findByProject(projectId);
		}
		
		throw new EntityNotFoundException("Project not in DB");
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

	public Project addStoryToBacklog(User user, Project project, Story story)
	{
		if(userIsOwner(user, project))
		{
			Story storyToSave = new Story(idGenerator.getNextId(), story.getName(), story.getDescription(), project, story.getStatus(), story.getPriority());
			storyRepository.save(storyToSave);

			return findByProjectId(project.getProjectId());
		}
		
		throw new PermissionDeniedException("User is not an owner");
	}

	public Project removeStoryFromBacklog(User user, Project project, Story story)
	{
		if(userIsOwner(user, project))
		{
			storyRepository.delete(story);
			return findByProjectId(project.getProjectId());
		}
		
		throw new PermissionDeniedException("User is not an owner");
	}

	public void removeProject(User user, Project project)
	{
		if (userIsOwner(user, project))
		{
			projectRepository.delete(project);
		}

		throw new PermissionDeniedException("Permission denied");
	}

	private boolean userIsOwner(User user, Project project)
	{
		return project.getTeam().getRole(user).equals(Role.OWNER);
	}

}
