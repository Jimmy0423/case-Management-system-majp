package se.majp.caseManagement.main;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.model.Priority;
import se.majp.caseManagement.model.Project;
import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Team;
import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.model.User;
import se.majp.caseManagement.repository.IssueRepository;
import se.majp.caseManagement.repository.ProjectRepository;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.TeamMemberRepository;
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
			TeamMemberRepository teamMemberRepository = context.getBean(TeamMemberRepository.class);
			IssueRepository issueRepository = context.getBean(IssueRepository.class);
			
			User user = new User("BoAhl@example.com", "Bo", "Ahl", "BoThaMaster");
			Project project = new Project(generator.getNextId(), "Get shit done!", "Lets do this shit");
			Project project2 = new Project(generator.getNextId(), "Get more shit done", "Lets never do this");
			Story story = new Story(generator.getNextId(), "Do shit", Priority.VERYHIGH, Story.Status.PENDING);
			Story story2 = new Story(generator.getNextId(), "Do shit", Priority.VERYHIGH, Story.Status.PENDING);
			
			TeamMember teamMember = new TeamMember(user, Role.MEMBER, project.getTeam());
			Issue issue = new Issue("Issue Ttitle", "You screwed up you idiot", user);
	
			userRepository.save(user);
			storyRepository.save(story);
			storyRepository.save(story2);
			projectRepository.save(project);
			projectRepository.save(project2);
			teamMemberRepository.save(teamMember);
			issueRepository.save(issue);
			
			teamMember.addStory(story).addStory(story2);
			teamMemberRepository.save(teamMember);
			
			Team team = project.getTeam().addTeamMember(teamMember);
			teamRepository.save(team);
			
			Team team2 = project2.getTeam().addTeamMember(teamMember);
			teamRepository.save(team2);
			
			story.addIssue(issue);
			storyRepository.save(story);
			
			user.addProject(project);
			user.addProject(project2);
			userRepository.save(user);
	
			storyRepository.findByTeam(team).forEach(System.out::println);
			System.out.println("----------------");
			storyRepository.findByUser(user).forEach(System.out::println);
			System.out.println("----------------");
			projectRepository.findAllProjectsForUser(user).forEach(System.out::println);
			System.out.println("----------------");
			storyRepository.findStoriesWithIssues().forEach(System.out::println);
			System.out.println("-----------------");
			storyRepository.findByDescriptionContaining("shit").forEach(System.out::println);
			System.out.println("-----------------");
			teamMemberRepository.findByTeam(team).forEach(System.out::println);
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
