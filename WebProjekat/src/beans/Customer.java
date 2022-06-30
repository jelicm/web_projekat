package beans;

import java.util.ArrayList;

import enums.Gender;
import enums.Role;

public class Customer extends User {

	private String membershipFee;
	private int points;
	private ArrayList<String> visitedSportFacilities;
	private CustomerType customerType;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth, String membershipFee, int points, CustomerType customerType) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.membershipFee = membershipFee;
		this.points = points;
		this.customerType = customerType;
		this.visitedSportFacilities = new ArrayList<String>();
	}
	
	public ArrayList<String> getVisitedSportFacilities() {
		return visitedSportFacilities;
	}

	public void setVisitedSportFacilities(ArrayList<String> visitedSportFacilities) {
		this.visitedSportFacilities = visitedSportFacilities;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getMembershipFee() {
		return membershipFee;
	}
	public void setMembershipFee(String membershipFee) {
		this.membershipFee = membershipFee;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addVisitedSportFacility(String facility){
		this.visitedSportFacilities.add(facility);
	}
	
}
