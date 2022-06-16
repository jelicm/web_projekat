package beans;

import java.util.ArrayList;

import enums.Gender;
import enums.Role;

public class Customer extends User {

	private MembershipFee membershipFee;
	private int points;
	private ArrayList<SportFacility> visitedSportFacilities;
	private CustomerType customerType;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth, MembershipFee membershipFee, int points, CustomerType customerType) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.membershipFee = membershipFee;
		this.points = points;
		this.customerType = customerType;
		this.visitedSportFacilities = new ArrayList<SportFacility>();
	}
	
	public ArrayList<SportFacility> getVisitedSportFacilities() {
		return visitedSportFacilities;
	}

	public void setVisitedSportFacilities(ArrayList<SportFacility> visitedSportFacilities) {
		this.visitedSportFacilities = visitedSportFacilities;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public MembershipFee getMembershipFee() {
		return membershipFee;
	}
	public void setMembershipFee(MembershipFee membershipFee) {
		this.membershipFee = membershipFee;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
}
