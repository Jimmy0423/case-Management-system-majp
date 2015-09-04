package se.majp.caseManagement.main;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Status;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.UserRepository;
import se.majp.caseManagement.service.UserService;
import se.majp.caseManagement.util.IdGenerator;

public class Main
{
	public static void main(String[] args)
	{
		IdGenerator generator = IdGenerator.getBuilder().characters('0', 'z').length(8).build();
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.majp.caseManagement");
			context.refresh();
			
			UserRepository userRepository = context.getBean(UserRepository.class);
			StoryRepository storyRepository = context.getBean(StoryRepository.class);
			ProjectRepository projectRepository = context.getBean(ProjectRepository.class);
			IssueRepository issueRepository = context.getBean(IssueRepository.class);
			UserService userService = context.getBean(UserService.class);
			
			User user = new User("BoAhl@example.com", "BoThaMaster", "Bo", "Ahl");
			Project project = new Project(generator.getNextId(), "Get shit done!", "Lets do this shit");
			Project project2 = new Project(generator.getNextId(), "Get more shit done", "Lets never do this");

			Story story = new Story(generator.getNextId(), "Do shit", project, Status.PENDING, Priority.HIGH);
			Story story2 = new Story(generator.getNextId(), "Do shit", project2, Status.PENDING, Priority.LOW);
			// user.addStory(story);
			// story.setUser(user);
			
			Issue issue = new Issue("Issue Ttitle", "You screwed up you idiot", story);
	
			user = userService.addUser(user);
			user.setPassword("new password");
			user = userService.updateUser(user);
			projectRepository.save(project);
			projectRepository.save(project2);
			storyRepository.save(story);
			storyRepository.save(story2);
			issueRepository.save(issue);
			user = userService.addStory(user, story2);
			user = userService.addStory(user, story);
			
			project.getTeam().addUser(user, Role.OWNER);
			project2.getTeam().addUser(user, Role.MEMBER);
			project.addStoryToBacklog(story);
			project.addStoryToBacklog(story2);
			
			projectRepository.save(project);
			projectRepository.save(project2);
			
			story.addIssue(issue);
			storyRepository.save(story);
			userService.removeUser(user);
	
			System.out.println("Find story by Project \n-------------------------");
			storyRepository.findByProject(project.getProjectId()).forEach(System.out::println);
			System.out.println("Find story by User \n-------------------------");
			storyRepository.findByUser(user.getUserId()).forEach(System.out::println);
			System.out.println("Find projects by User \n-------------------------");
			projectRepository.findAllProjectsForUser(user).forEach(System.out::println);
			System.out.println("Find story with Issue \n-------------------------");
			storyRepository.findStoriesWithIssues().forEach(System.out::println);
			System.out.println("Find story by Description \n-------------------------");
			storyRepository.findByDescriptionContaining("shit").forEach(System.out::println);
			System.out.println("-----------------");
		}
	}

	private static void testIds(IdGenerator generator)
	{
		String[] generatedIds = new String[10000000];

		for (int i = 0; i < generatedIds.length; i++)
		{
			generatedIds[i] = generator.getNextId();
		}

		System.out.println("Sorting");
		Arrays.sort(generatedIds);
		System.out.println("Done sorting");
		String lastId = "";
		int i = 0;

		for (String id : generatedIds)
		{
			if (id.equals(lastId))
			{
				System.out.println("id: " + id + "\nlast id: " + lastId);
				i++;
			}

			lastId = id;
		}

		System.out.println(i);
	}
}
