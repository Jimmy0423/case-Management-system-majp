package se.majp.cms.main;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.majp.cms.model.Priority;
import se.majp.cms.model.Status;
import se.majp.cms.model.Story;
import se.majp.cms.repository.StoryRepository;
import se.majp.cms.repository.UserRepository;
import se.majp.cms.service.ProjectService;
import se.majp.cms.service.StoryService;
import se.majp.cms.service.UserService;

public class Main
{

	public static void main(String[] args)
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.majp");
			context.refresh();

			UserService userService = context.getBean(UserService.class);
			UserRepository userRepo = context.getBean(UserRepository.class);
			ProjectService projectService = context.getBean(ProjectService.class);
			StoryService storyService = context.getBean(StoryService.class);
			
//			storyService.changeStatus("YFm29qCE", "TEST");
			
			StoryRepository storyRepository = context.getBean(StoryRepository.class);
//			storyRepository.findByDescriptionContaining("", new PageRequest(0, 1)).forEach(System.out::println);
			
//			Story story = storyRepository.findByStoryId("YFm29qCE");
//			System.out.println(story.getModificationTime());
			
			storyService.findAllStoriesByStatusAndDate("INPROGRESS", "2015-9-16", "2015-9-18").forEach(System.out::println);
			
//			Calendar testDate = Calendar.getInstance();
//			testDate.set(2015, 8, 16);
//			
//			if (story.getModificationTime().before(testDate.getTime()))
//			{
//				System.out.println("testDate: " + testDate.getTime());
//				System.out.println("storyDate: " + story.getModificationTime());
//			}

//			Slice<User> users = userRepo.findAll(new PageRequest(9, 10));
//
//			System.out.println(users.getContent().size());

			// userService.findByFirstNameOrLastNameOrEmail("email").forEach(System.out::println);
		}

	}
	
	private static void addStories(StoryService storyService)
	{
		for (int i = 0; i < 30; i++)
		{
			Status status = null;
			
			if (i < 5)
			{
				status = Status.DONE;
			}
			else if (i < 10)
			{
				status = Status.INPROGRESS;
			}
			else if (i < 15)
			{
				status = Status.ISSUED;
			}
			else if (i < 20)
			{
				status = Status.PENDING;
			}
			else if (i < 25)
			{
				status = Status.TEST;
			}
			
			storyService.addStoryToBacklog("di9LAu45", new Story("Best story " + i, "Best description " + i, 
					status, Priority.NORMAL));
		}
		
	}

	// private static void addUsers(UserService userService)
	// {
	// for (int i = 0; i < 100; i++)
	// {
	// userService.addOrUpdateUser(new User("email " + i, "password " + i,
	// "firstname " + i, "lastname " + i));
	// }
	// }

}
