package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import enums.FacilityType;
import enums.Role;
import enums.Status;
import enums.TrainingType;
import enums.TypeName;

@Path("/enums")
public class EnumService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public EnumService() {
		
	}
	
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Status[] getStatusValues() {
		Status[] values = Status.values();
		return values;
	}
	
	@GET
	@Path("/facilityType")
	@Produces(MediaType.APPLICATION_JSON)
	public FacilityType[] getFacilityType() {
		FacilityType[] values = FacilityType.values();
		return values;
	}
	
	@GET
	@Path("/trainingType")
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingType[] getTrainingType() {
		TrainingType[] values = TrainingType.values();
		return values;
	}
	
	@GET
	@Path("/typeName")
	@Produces(MediaType.APPLICATION_JSON)
	public TypeName[] getCustomerType() {
		TypeName[] values = TypeName.values();
		return values;
	}
	
	@GET
	@Path("/role")
	@Produces(MediaType.APPLICATION_JSON)
	public Role[] getRole() {
		Role[] values = Role.values();
		return values;
	}
	
	
	
	

}
