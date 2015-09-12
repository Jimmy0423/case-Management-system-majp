package se.majp.cms.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import se.majp.cms.model.User;
import se.majp.cms.repository.StoryRepository;
import se.majp.cms.repository.UserRepository;
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
			
			StoryRepository storyRepository = context.getBean(StoryRepository.class);
			storyRepository.findByDescriptionContaining("", new PageRequest(0, 1)).forEach(System.out::println);

//			Slice<User> users = userRepo.findAll(new PageRequest(9, 10));
//
//			System.out.println(users.getContent().size());

			// userService.findByFirstNameOrLastNameOrEmail("email").forEach(System.out::println);
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
