package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ManagerDAO;
import dao.SportFacilityDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
import beans.Manager;
import beans.SportFacility;
import beans.Training;
import beans.TrainingHistory;


@Path("/sportFacilities")
public class SportFacilityService {
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	public static ArrayList<Training> managerTrainings = new ArrayList<Training>();
	
	public SportFacilityService() {
		
	}
	
	@PostConstruct
	public void init() {
    	String contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("sportFacilityDAO") == null) {
			ctx.setAttribute("sportFacilityDAO", new SportFacilityDAO(contextPath));
		}
		if (ctx.getAttribute("managerDAO") == null) {
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
		if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO(contextPath));
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO(contextPath));
		}
	}
	
	@POST
	@Path("/createSportFacility/{manager}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSportFacility(SportFacility sportFacility, @PathParam("manager") String manager) {
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		SportFacility sp = sportFacilityDAO.findSportFacility(sportFacility.getName());
		if (sp == null) {
			sportFacilityDAO.addSportFacility(sportFacility);
			
			ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
			String[] words = manager.split(" ");
			for(Manager m : managerDAO.findAllManagers()) {
				if(m.getName().equals(words[0]) && m.getSurname().equals(words[1])) {
					Manager newManager = m;
					newManager.setSportFacility(sportFacility.getName());
					managerDAO.updateManager(newManager, newManager.getName());
					break;
				}
			}
			
			return Response.status(200).entity("adminMainPage.html").build();
		}
		
		return Response.status(400).build();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SportFacility> getSportFacilities() {
		SportFacilityDAO dao = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		ArrayList<SportFacility> visibleSportFacilities = new ArrayList<SportFacility>();
		for(SportFacility sf : dao.findAllSportFacilitiesSorted())
			if(!sf.isDeleted())
				visibleSportFacilities.add(sf);
		return visibleSportFacilities;
	}
	
	@GET
	@Path("/review/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reviewSportFacility(@PathParam("name") String name) {
		SportFacilityDAO dao = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		SportFacility sf = dao.findSportFacility(name);
		if(sf != null) {
			ctx.setAttribute("reviewedSportFacility", sf);
			return Response.status(200).entity("sportFacilityInfo.html").build();
		}
		return Response.status(400).build();
	}
	
	@GET
	@Path("/reviewed")
	@Produces(MediaType.APPLICATION_JSON)
	public SportFacility getReviewedSportFacility() {
		SportFacility sf = (SportFacility)ctx.getAttribute("reviewedSportFacility");
		return sf;
	}
	
	@GET
	@Path("/trainingsForSportFacilityManager/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> trainingsForSportFacilityManager(@PathParam("name") String name) {
		SportFacility sportFacility;
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		sportFacility = sportFacilityDAO.findSportFacility(name);
		managerTrainings = new ArrayList<Training>();
		if(sportFacility != null) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
			for(Training t : trainingDAO.findAllTrainings()) {
				int numOfTrainings = 0;
				for(TrainingHistory th : thDAO.findAllTrainingHistories()) {
					if(th.getTraining().equals(t.getName()))
						numOfTrainings++;
				}
				if(t.getSportFacility().equals(sportFacility.getName())) {
					for(int i = 0; i < numOfTrainings; i++)
						managerTrainings.add(t);
				}
			}
			
		}
		return managerTrainings;
	}
	
	@GET
	@Path("/trainingsForSportFacility/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> trainingsForSportFacility(@PathParam("name") String name) {
		SportFacility sportFacility;
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		sportFacility = sportFacilityDAO.findSportFacility(name);
		ArrayList <Training> trainings = new ArrayList<Training>();
		if(sportFacility != null) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			for(Training t : trainingDAO.findAllTrainings()) {
				if(t.getSportFacility().equals(sportFacility.getName())) {
					trainings.add(t);
				}
			}

		}

		return trainings;
	}
	
	@GET
	@Path("/getTrainingDates")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDates() {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		for(TrainingHistory th : thDAO.findAllTrainingHistories()){
			for(Training t : managerTrainings){
				if(t.getName().equals(th.getTraining())) {
					dates.add(th.getApplicationDateAndTime());
					break;
				}
			}
		}
		return dates;
	}
	
	
	@GET
	@Path("/getAllTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTraining() {
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO tDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		for(Training t : tDAO.findAllTrainings())
		{
			if(!t.isDeleted())
				trainings.add(t);		
		}
		return trainings;
	}
	
	@DELETE
	@Path("/deleteSportFacility/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSportFacility(@PathParam("name") String name) {
		SportFacilityDAO sportFacilityDAO = (SportFacilityDAO) ctx.getAttribute("sportFacilityDAO");
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		SportFacility sf = sportFacilityDAO.findSportFacility(name);
		for(Manager m : managerDAO.findAllManagers())
		{
			if(m.getSportFacility() == null)
				continue;
			if(m.getSportFacility().equals(sf.getName()))
			{
				m.setSportFacility(null);
				managerDAO.addManager(m);
				break;
			}
		}
		
		for(Training t: trainingDAO.findAllTrainings())
		{	
			if(t.getSportFacility().equals(sf.getName()))
			{
				t.setDeleted(true);
				trainingDAO.addTraining(t);
			}
				
		}
		
		sf.setDeleted(true);
		sportFacilityDAO.addSportFacility(sf);
		return Response.status(200).build(); 
	}
	
	
}
