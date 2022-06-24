package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.SportFacilityDAO;
import beans.SportFacility;


@Path("/sportFacilities")
public class SportFacilityService {
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public SportFacilityService() {
		
	}
	
	@PostConstruct
	public void init() {
    	String contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("sportFacilityDAO") == null) {
			ctx.setAttribute("sportFacilityDAO", new SportFacilityDAO(contextPath));
		}
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

}
