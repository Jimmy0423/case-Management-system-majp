package se.majp.cms.web.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;

import se.majp.cms.model.Issue;
import se.majp.cms.service.IssueService;

@Path("issues")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Scope("request")
public class IssueWebService
{
	@Autowired
	private IssueService issueService;

	@GET
	public Response findAllIssues(@DefaultValue("0") @QueryParam("page") final int page,
			@DefaultValue("10") @QueryParam("size") final int size,
			@DefaultValue("desc") @QueryParam("order") final String order)
	{
		Slice<Issue> issues = issueService.findAllIssues(new PageRequest(page, size, Direction.fromStringOrNull(order)));
		
		GenericEntity<List<Issue>> entity = new GenericEntity<List<Issue>>(issues.getContent())
		{
		};
		
		return Response.ok(entity).build();
	}
}
