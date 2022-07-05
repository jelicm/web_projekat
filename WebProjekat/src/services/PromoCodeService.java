package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.PromoCode;
import dao.PromoCodeDAO;

@Path("/promoCodes")
public class PromoCodeService {
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public PromoCodeService() {
		
	}
	
	@PostConstruct
	public void init() {
    	String contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("promoCodeDAO") == null) {
			ctx.setAttribute("promoCodeDAO", new PromoCodeDAO(contextPath));
		}
	}
	
	@POST
	@Path("/createPromoCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSportFacility(PromoCode promoCode) {
		PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		PromoCode pc = promoCodeDAO.findPromoCode(promoCode.getIdentifier());
		if (pc == null) {
			promoCodeDAO.addPromoCode(promoCode);
			return Response.status(200).entity("adminMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	
}
