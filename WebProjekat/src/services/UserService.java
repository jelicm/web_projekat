package services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Admin;
import beans.Coach;
import beans.Customer;
import beans.Manager;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import dao.AdminDAO;
import dao.CoachDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;

@Path("/users")
public class UserService {
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
    	String contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("customerDAO") == null) {
			ctx.setAttribute("customerDAO", new CustomerDAO(contextPath));
		}
		if (ctx.getAttribute("adminDAO") == null) {
			ctx.setAttribute("adminDAO", new AdminDAO(contextPath));
		}
		if (ctx.getAttribute("coachDAO") == null) {
			ctx.setAttribute("coachDAO", new CoachDAO(contextPath));
		}
		if (ctx.getAttribute("managerDAO") == null) {
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Customer customer) {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = customerDAO.findCustomer(customer.getUsername());
		if (c == null) {
			customerDAO.addOrModifyCustomer(customer);
			return Response.status(200).entity("index.html").build();
		}
		return Response.status(400).build();
	}
	
	@POST
	@Path("/registerCoach")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCoach(Coach coach) {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = customerDAO.findCustomer(coach.getUsername());
		if (c != null) {
			return Response.status(400).build();
		}
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		Coach co = coachDAO.findCoach(coach.getUsername());
		if (co == null) {
			coachDAO.addOrModifyCoach(coach);
			return Response.status(200).entity("adminMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@POST
	@Path("/registerManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerManager(Manager manager) {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = customerDAO.findCustomer(manager.getUsername());
		if (c != null) {
			return Response.status(400).build();
		}
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		Manager m = managerDAO.findManager(manager.getUsername());
		if (m == null) {
			managerDAO.addOrModifyManager(manager);
			return Response.status(200).entity("adminMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@GET
	@Path("/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer customer = customerDAO.findCustomer(username);
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		Coach coach = coachDAO.findCoach(username);
		AdminDAO adminDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		Admin admin = adminDAO.findAdmin(username);
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		Manager manager = managerDAO.findManager(username);
		
		if(customer != null) {
			if(customer.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", customer);
				return Response.status(200).entity("customerMainPage.html").build();
			}
		}
		
		if(coach != null) {
			if(coach.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", coach);
				return Response.status(200).entity("coachMainPage.html").build();
			}
		}
		
		if(admin != null) {
			if(admin.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", admin);
				return Response.status(200).entity("adminMainPage.html").build();
			}
		}
		
		if(manager != null) {
			if(manager.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", manager);
				return Response.status(200).entity("managerMainPage.html").build();
			}
		}	
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateAdmin/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdmin(Admin admin, @PathParam("username") String username){
		AdminDAO adminDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		admin = adminDAO.updateAdmin(admin, username);
		if(admin != null) {
			request.getSession().setAttribute("loggedInUser", admin);
			return Response.status(200).entity("adminMainPage.html").build();
		}
		
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCoach/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCoach(Coach coach, @PathParam("username") String username){
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		coach = coachDAO.updateCoach(coach, username);
		if(coach != null) {
			request.getSession().setAttribute("loggedInUser", coach);
			return Response.status(200).entity("coachMainPage.html").build();
		}
		
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCustomer/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @PathParam("username") String username){
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		customer = customerDAO.updateCustomer(customer, username);
		if(customer != null) {
			request.getSession().setAttribute("loggedInUser", customer);
			return Response.status(200).entity("customerMainPage.html").build();
		}
		
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateManager/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateManager(Manager manager, @PathParam("username") String username){
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		manager = managerDAO.updateManager(manager, username);
		if(manager != null) {
			request.getSession().setAttribute("loggedInUser", manager);
			return Response.status(200).entity("managerMainPage.html").build();
		}
		
		return Response.status(400).build();
	}
	
	@GET
	@Path("/loggedInAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public Admin getAdmin() {
		Admin a = (Admin)request.getSession().getAttribute("loggedInUser");
		return a;
	}
	
	@GET
	@Path("/loggedInCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer() {
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		return c;
	}
	
	@GET
	@Path("/loggedInManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManager() {
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		return m;
	}
	
	@GET
	@Path("/loggedInCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public Coach getCoach() {
		Coach c = (Coach)request.getSession().getAttribute("loggedInUser");
		return c;
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		request.getSession().removeAttribute("loggedInUser");
		return Response.status(200).build();
	}
	


}
