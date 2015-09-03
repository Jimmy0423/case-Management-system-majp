package se.majp.caseManagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.model.Role;
import se.majp.caseManagement.model.Story;
import se.majp.caseManagement.model.Story.Status;
import se.majp.caseManagement.model.TeamMember;
import se.majp.caseManagement.repository.StoryRepository;
import se.majp.caseManagement.repository.TeamMemberRepository;

public class TeamMemberService
{
	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private TeamMemberRepository teamMemberRepository;

	public TeamMember add(TeamMember teamMember, Story story)
	{
		storyRepository.save(story);
		teamMember.addStory(story);
		return teamMemberRepository.save(teamMember);
	}

	public TeamMember remove(TeamMember teamMember, Story story)
	{
		storyRepository.findOne(story.getId()).setStatus(Status.DONE);
		teamMember.removeStory(story);
		return teamMemberRepository.save(teamMember);
	}

	public TeamMember updateRole(TeamMember teamMember, Role role)
	{
		teamMember.setRole(role);
		return teamMemberRepository.save(teamMember);
		
	}

}
