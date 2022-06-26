package services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Admin;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import beans.SportFacility;
import beans.Training;
import beans.User;

import java.util.ArrayList;
import java.util.Collection;

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
import dao.SportFacilityDAO;
import dao.TrainingDAO;

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
    	if (ctx.getAttribute("sportFacilityDAO") == null) {
			ctx.setAttribute("sportFacilityDAO", new SportFacilityDAO(contextPath));
    	}
    	if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO(contextPath));
    	}
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
			customerDAO.addCustomer(customer);
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
			coachDAO.addCoach(coach);
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
			managerDAO.addManager(manager);
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
		
		if(coach != null && !coach.isDeleted()) {
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
		
		if(manager != null && !manager.isDeleted()) {
			if(manager.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", manager);
				return Response.status(200).entity("managerMainPage.html").build();
			}
		}	
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateAdmin/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdmin(Admin admin, @PathParam("username") String username){
		AdminDAO adminDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		Admin a = adminDAO.findAdmin(admin.getUsername());
		if((a != null && a.getUsername().equals(username)) || a == null) {
			adminDAO.updateAdmin(admin,username);
			request.getSession().setAttribute("loggedInUser", admin);
			return Response.status(200).entity("adminMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCoach/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCoach(Coach coach, @PathParam("username") String username){
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		Coach c = coachDAO.findCoach(coach.getUsername());
		if((c != null && c.getUsername().equals(username)) || c == null) {
			coachDAO.updateCoach(coach,username);
			request.getSession().setAttribute("loggedInUser", coach);
			return Response.status(200).entity("coachMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCustomer/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @PathParam("username") String username){
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = customerDAO.findCustomer(customer.getUsername());
		if((c != null && c.getUsername().equals(username)) || c == null) {
			customerDAO.updateCustomer(customer,username);
			request.getSession().setAttribute("loggedInUser", customer);
			return Response.status(200).entity("customerMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateManager/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateManager(Manager manager, @PathParam("username") String username){
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		Manager m = managerDAO.findManager(manager.getUsername());
		if((m != null && m.getUsername().equals(username)) || m == null) {
			managerDAO.updateManager(manager,username);
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
	
	@GET
	@Path("/registeredUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getRegisteredUsers() {
		ArrayList<User> users = new ArrayList<User>();
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		for (Customer c : customerDAO.findAllCustomers()) {
			users.add((User)c);
		};
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		for(Coach c : coachDAO.findAllCoaches()) {
			if(!c.isDeleted())
				users.add((User)c);
		};
		AdminDAO adminDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		for (Admin a : adminDAO.findAllAdmins()) {
			users.add((User)a);
		};
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		for(Manager m : managerDAO.findAllManagers()) {
			if(!m.isDeleted())
				users.add((User)m);
		};
		return users;
	}

	@GET
	@Path("/managerSportFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public SportFacility managerSportFacility() {
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportFacilityName = m.getSportFacility();
		SportFacility sportFacility = sportFacilityDAO.findSportFacility(sportFacilityName);
		return sportFacility;
	}
	
	@GET
	@Path("/customersForSportFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> customersForSportFacility() {
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportFacilityName = m.getSportFacility();
		SportFacility sportFacility = sportFacilityDAO.findSportFacility(sportFacilityName);
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		if(sportFacility != null) {
			CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
			for(Customer c : customerDAO.findAllCustomers()) {
				for(String sf : c.getVisitedSportFacilities()) {
					if(sf.equals(sportFacility.getName())) {
						customers.add(c);
						break;
					}
				}
			}
		}
		
		return customers;
	}
	
	@GET
	@Path("/coachesForSportFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Coach> coachesForSportFacility() {
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportFacilityName = m.getSportFacility();
		SportFacility sportFacility = sportFacilityDAO.findSportFacility(sportFacilityName);
		
		ArrayList<Coach> coaches = new ArrayList<Coach>();
		ArrayList<String> coachNames = new ArrayList<String>();
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		
		if(sportFacility != null) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			for(Training t : trainingDAO.findAllTrainings()) {
				if(t.getSportFacility().equals(sportFacility.getName())) {
					coachNames.add(t.getCoach());
				}
			}
		}
		if(coachNames != null) {
			for(String name : coachNames)
				coaches.add(coachDAO.findCoach(name));
		}
		return coaches;
	}
	
	@GET
	@Path("/availableManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> availableManagers() {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		ArrayList<Manager> availableManagers = new ArrayList<Manager>(); 
		for(Manager m : managerDAO.findAllManagers()) {
			if(m.getSportFacility() == null)
				availableManagers.add(m);
		}
		return availableManagers;
	}
	
}
