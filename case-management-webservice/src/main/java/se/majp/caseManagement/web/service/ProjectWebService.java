package se.majp.caseManagement.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import se.majp.caseManagement.service.ProjectServiceImp;

@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectWebService
{
	@Autowired
	private ProjectServiceImp projectService;
}
