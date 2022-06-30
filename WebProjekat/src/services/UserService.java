package services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Admin;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import beans.MembershipFee;
import beans.SportFacility;
import beans.Training;
import beans.TrainingHistory;
import beans.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import dao.MembershipFeeDAO;
import dao.SportFacilityDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
import enums.MembershipFeeStatus;
import enums.MembershipFeeType;
import enums.TrainingType;

@Path("/users")
public class UserService {
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	public static ArrayList<Training> customerTrainings = new ArrayList<Training>();
	public static ArrayList<Training> coachPersonalTrainings = new ArrayList<Training>();
	public static ArrayList<Training> coachGroupTrainings = new ArrayList<Training>();
	
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
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO(contextPath));
		}
		if (ctx.getAttribute("membershipFeeDAO") == null) {
			ctx.setAttribute("membershipFeeDAO", new MembershipFeeDAO(contextPath));
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
	
	@GET
	@Path("/allCoaches")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coach> allCoaches() {
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		return coachDAO.findAllCoaches();
	}
	
	@POST
	@Path("/createTraining/{coach}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTraining(Training training, @PathParam("coach") String coach) {
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		String sp = m.getSportFacility();
		if (sp != null) {
			training.setSportFacility(sp);
			
			CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
			String[] words = coach.split(" ");
			for(Coach c : coachDAO.findAllCoaches()) {
				if(c.getName().equals(words[0]) && c.getSurname().equals(words[1])) {
					training.setCoach(c.getUsername());
					break;
				}
			}
			
			Training tr = trainingDAO.findTraining(training.getName());
			
			if(tr == null) {
				tr = trainingDAO.addTraining(training);
				return Response.status(200).entity("managerMainPage.html").build();
			}
		}
		
		return Response.status(400).build();
	}
	
	@GET
	@Path("/trainingReview/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response trainingReview(@PathParam("name") String name) {
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Training t = trainingDAO.findTraining(name);
		if(t != null)
		{	
			ctx.setAttribute("Training", t);
			return Response.status(200).entity("updateTraining.html").build();
		}
		return Response.status(400).build();
	}
	
	@GET
	@Path("/getTraining/")
	@Produces(MediaType.APPLICATION_JSON)
	public Training getTraining() {
		
		Training t = (Training) ctx.getAttribute("Training");
		return t;
	}
	
	@PUT
	@Path("/updateTraining/{coach}/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTraining(Training training, @PathParam("coach") String coach, @PathParam("name") String name) {
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		String sp = m.getSportFacility();
		SportFacility sf = sportFacilityDAO.findSportFacility(sp);
		if (sf != null) {
			CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
			String[] words = coach.split(" ");
			for(Coach c : coachDAO.findAllCoaches()) {
				if(c.getName().equals(words[0]) && c.getSurname().equals(words[1])) {
					training.setCoach(c.getUsername());
					break;
				}
			}
			
		Training tr = trainingDAO.findTraining(training.getName());
		
		if((tr != null && tr.getName().equals(name)) || tr == null) {
			trainingDAO.updateTraining(training, name);
			if(training.getName() != name) {
				ArrayList<String> trainings = sf.getTrainings();
				trainings.remove(name);
				trainings.add(training.getName());
				sportFacilityDAO.addSportFacility(sf);
			}
			return Response.status(200).entity("managerMainPage.html").build();
		}
		
		}
		return Response.status(400).build();
	}
	
	@GET
	@Path("/getTrainingsForCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainingsForCustomer() throws ParseException {
		customerTrainings = new ArrayList<Training>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Customer c = (Customer) request.getSession().getAttribute("loggedInUser");
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		    Date d1 = sdformat.parse(th.getApplicationDateAndTime());
		    Date d2 = new Date();
		    String d2Str = sdformat.format(d2);
		    d2 = sdformat.parse(d2Str);
		    long difference_In_Time = d2.getTime() - d1.getTime();
		    long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			if(th.getCustomer().equals(c.getUsername()) && difference_In_Days <= 30)
			{
				Training t = trainingDAO.findTraining(th.getTraining());
				customerTrainings.add(t);
			}
		}
		return customerTrainings;
	}
	
	@GET
	@Path("/getTrainingDates")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDates() {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		Customer c = (Customer) request.getSession().getAttribute("loggedInUser");
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			if(th.getCustomer().equals(c.getUsername()))
			{
				
				for(Training t : customerTrainings){
					if(t.getName().equals(th.getTraining())) {
						dates.add(th.getApplicationDateAndTime());
						break;
					}
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getSportFacilityType")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getSportFacilityType() {
		ArrayList<String> types = new ArrayList<String>();
		SportFacilityDAO sfDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		for(Training t : customerTrainings){
			for(SportFacility sf : sfDAO.findAllSportFacilities()) {
				if(sf.getName().equals(t.getSportFacility())) {
					types.add(sf.getType().toString());
					break;
				}
			}
		}
		return types;
	}
	
	@GET
	@Path("/getPersonalTrainingsForCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getPersonalTrainingsForCoach(){
		coachPersonalTrainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Coach c = (Coach) request.getSession().getAttribute("loggedInUser");
		for(Training t : trainingDAO.findAllTrainings()){
			if(t.getCoach().equals(c.getUsername()) && t.getTrainingType() == TrainingType.PERSONALNI)
			{
				coachPersonalTrainings.add(t);
			}
		}
		return coachPersonalTrainings;
	}
	
	@GET
	@Path("/getGroupTrainingsForCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getGroupTrainingsForCoach(){
		coachGroupTrainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Coach c = (Coach) request.getSession().getAttribute("loggedInUser");
		for(Training t : trainingDAO.findAllTrainings()){
			if(t.getCoach().equals(c.getUsername()) && t.getTrainingType() == TrainingType.GRUPNI)
			{
				coachGroupTrainings.add(t);
			}
		}
		return coachGroupTrainings;
	}
	
	@GET
	@Path("/getPersonalTrainingDatesForCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDatesForCoach() {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		Coach c = (Coach) request.getSession().getAttribute("loggedInUser");
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			if(th.getCoach().equals(c.getUsername()))
			{
				
				for(Training t : coachPersonalTrainings){
					if(t.getName().equals(th.getTraining())) {
						dates.add(th.getApplicationDateAndTime());
						break;
					}
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getGroupTrainingDatesForCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getGroupTrainingDatesForCoach() {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		Coach c = (Coach) request.getSession().getAttribute("loggedInUser");
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			if(th.getCoach().equals(c.getUsername()))
			{
				
				for(Training t : coachGroupTrainings){
					if(t.getName().equals(th.getTraining())) {
						dates.add(th.getApplicationDateAndTime());
						break;
					}
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getSportFacilityTypeForPersonal")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getSportFacilityTypeForPersonal() {
		ArrayList<String> types = new ArrayList<String>();
		SportFacilityDAO sfDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		for(Training t : coachPersonalTrainings){
			for(SportFacility sf : sfDAO.findAllSportFacilities()) {
				if(sf.getName().equals(t.getSportFacility())) {
					types.add(sf.getType().toString());
					break;
				}
			}
		}
		return types;
	}
	
	@GET
	@Path("/getSportFacilityTypeForGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getSportFacilityTypeForGroup() {
		ArrayList<String> types = new ArrayList<String>();
		SportFacilityDAO sfDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		for(Training t : coachGroupTrainings){
			for(SportFacility sf : sfDAO.findAllSportFacilities()) {
				if(sf.getName().equals(t.getSportFacility())) {
					types.add(sf.getType().toString());
					break;
				}
			}
		}
		return types;
	}
	
	@GET
	@Path("/cancelTraining/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelTraining (@PathParam("name") String name) throws ParseException {
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		TrainingHistory trHistory = null;
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			if(th.getTraining().equals(name)) {
				trHistory = th;
				break;
			}
		}
		if(trHistory != null) {
			SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		    Date d1 = sdformat.parse(trHistory.getApplicationDateAndTime());
		    Date d2 = new Date();
		    String d2Str = sdformat.format(d2);
		    d2 = sdformat.parse(d2Str);
		    long difference_In_Time = d1.getTime() - d2.getTime();
		    long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		    for(Training t : coachPersonalTrainings){
				if(t.getName().equals(name) && difference_In_Days >= 2) {
					thDAO.deleteTrainingHistory(trHistory.getName());
					return Response.status(200).entity("coachMainPage.html").build();
				}
			}
		}
		return Response.status(400).build();
	}
	
	@POST
	@Path("/createMembershipFee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMembershipFee(MembershipFee membershipFee) {
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		MembershipFeeDAO mfDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		int numOfFees = mfDAO.findAllMembershipFees().size();
		numOfFees++;
		membershipFee.setCustomer(c.getUsername());
		membershipFee.setIdentifier(membershipFee.getIdentifier() + numOfFees);
		request.getSession().setAttribute("membershipFee", membershipFee);
		return Response.status(200).entity("membershipFeeReview.html").build();
	}
	
	@POST
	@Path("/payMembershipFee/{discount}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response payMembershipFee(@PathParam("discount") double discount) {
		MembershipFee mf = (MembershipFee)request.getSession().getAttribute("membershipFee");
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		mf.setPrice((int)(mf.getPrice()-mf.getPrice()*discount));
		mf.setMembershipFeeStatus(MembershipFeeStatus.AKTIVNA);
		MembershipFeeDAO mfDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		for(MembershipFee fee : mfDAO.findAllMembershipFees()) {
			if(fee.getCustomer().equals(c.getUsername())) {
				fee.setMembershipFeeStatus(MembershipFeeStatus.NEAKTIVNA);
				mfDAO.updateMembershipFee(fee);
				break;
			}
		}
		mfDAO.addMembershipFee(mf);
		return Response.status(200).entity("customerMainPage.html").build();
	}
}
