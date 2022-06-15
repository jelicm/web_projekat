package beans;

import java.util.ArrayList;

public class Customer extends User {
	protected int membershipFee;
	protected int points;
	ArrayList<SportFacility> visitedSportFacilities;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String name, String surname, String geneder, String role,
			String dateOfBirth, int membershipFee, int points) {
		super(username, password, name, surname, geneder, role, dateOfBirth);
		this.membershipFee = membershipFee;
		this.points = points;
		visitedSportFacilities = new ArrayList<SportFacility>();
	}



	public int getMembershipFee() {
		return membershipFee;
	}
	public void setMembershipFee(int membershipFee) {
		this.membershipFee = membershipFee;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
}
