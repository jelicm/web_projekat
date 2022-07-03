package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Comment;
import beans.SportFacility;
import dao.CommentDAO;
import dao.SportFacilityDAO;
import enums.Permission;

@Path("/comments")
public class CommentService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public CommentService() {
		
	}
	
	@PostConstruct
	public void init() {
    	String contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("commentDAO") == null) {
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/waitingForApproval")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> waitingForApproval() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for(Comment c : dao.findAllComments())
			if(c.getPermission() == Permission.NA_CEKANJU)
				comments.add(c);
		return comments;
	}
	
	@GET
	@Path("/approvedComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> approvedComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for(Comment c : dao.findAllComments())
			if(c.getPermission() == Permission.ODOBREN)
				comments.add(c);
		return comments;
	}
	
	@GET
	@Path("/approvedCommentsForFacility/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> approvedCommentsForFacility(@PathParam("name") String name) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for(Comment c : dao.findAllComments())
			if(c.getPermission() == Permission.ODOBREN && c.getSportFacility().equals(name))
				comments.add(c);
		return comments;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> allComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAllComments();
	}
	
	@POST
	@Path("/approvedComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approvedComment(Comment c) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		c.setPermission(Permission.ODOBREN);
		dao.updateComment(c);
		return Response.status(200).entity("commentApproval.html").build();
	}
	
	@POST
	@Path("/declinedComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response declinedComment(Comment c) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		c.setPermission(Permission.ODBIJEN);
		dao.updateComment(c);
		return Response.status(200).entity("commentApproval.html").build();
	}
}

