package se.majp.caseManagement.main;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.TeamRepository;
import se.majp.caseManagement.repository.UserRepository;
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
			TeamRepository teamRepository = context.getBean(TeamRepository.class);
			StoryRepository storyRepository = context.getBean(StoryRepository.class);
			ProjectRepository projectRepository = context.getBean(ProjectRepository.class);
			User user = new User("BoAhl@example.com", "Bo", "Ahl", "BoThaMaster");
			Project project = new Project(generator.getNextId(), "Get shit done!", "Lets do this shit");
			Story story = new Story(generator.getNextId(), "Do shit", Priority.VERYHIGH, Story.Status.PENDING);
			Story story2 = new Story(generator.getNextId(), "Do shit", Priority.VERYHIGH, Story.Status.PENDING);
			Team team = project.getTeam().addUser(user).addStory(story).addStory(story2);
			
			userRepository.save(user);
			storyRepository.save(story);
			storyRepository.save(story2);
			projectRepository.save(project);
			
			user.addStory(story);
			userRepository.save(user);
			
			storyRepository.findByTeam(team).forEach(System.out::println);
			System.out.println("----------------");
			storyRepository.findByUser(user).forEach(System.out::println);
			System.out.println("----------------");
			projectRepository.findAllProjectsForUser(user).forEach(System.out::println);
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
