package se.majp.caseManagement.web.service;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import se.majp.caseManagement.model.Issue;
import se.majp.caseManagement.service.IssueService;

@Path("issues")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Scope("request")
public class IssueWebService
{
	@Autowired
	private IssueService issueService;
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	public Response addIssue(Issue issue)
	{
		issue = issueService.addIssue(issue);
		URI location = uriInfo.getAbsolutePathBuilder().path(issue.getIssueId()).build();
		
		return Response.created(location).build();
	}
	
	@PUT
	public Response updateIssue(Issue issue)
	{
		issueService.updateIssue(issue);
		
		return Response.noContent().build();
	}
}
