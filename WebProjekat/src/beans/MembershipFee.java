package beans;

import enums.MembershipFeeStatus;
import enums.MembershipFeeType;

public class MembershipFee {
	private String identifier;
	private MembershipFeeType membershipFeeType;
	private String paymentDate;
	private String expirationDateAndTime;
	private int price;
	private Customer customer;
	private MembershipFeeStatus membershipFeeStatus;
	private String numberOfAppointments;
	
	public MembershipFee() {
	}

	public MembershipFee(String identifier, MembershipFeeType membershipFeeType, String paymentDate,
			String expirationDateAndTime, int price, Customer customer, MembershipFeeStatus membershipFeeStatus,
			String numberOfAppointments) {
		super();
		this.identifier = identifier;
		this.membershipFeeType = membershipFeeType;
		this.paymentDate = paymentDate;
		this.expirationDateAndTime = expirationDateAndTime;
		this.price = price;
		this.customer = customer;
		this.membershipFeeStatus = membershipFeeStatus;
		this.numberOfAppointments = numberOfAppointments;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public MembershipFeeType getMembershipFeeType() {
		return membershipFeeType;
	}

	public void setMembershipFeeType(MembershipFeeType membershipFeeType) {
		this.membershipFeeType = membershipFeeType;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getExpirationDateAndTime() {
		return expirationDateAndTime;
	}

	public void setExpirationDateAndTime(String expirationDateAndTime) {
		this.expirationDateAndTime = expirationDateAndTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public MembershipFeeStatus getMembershipFeeStatus() {
		return membershipFeeStatus;
	}

	public void setMembershipFeeStatus(MembershipFeeStatus membershipFeeStatus) {
		this.membershipFeeStatus = membershipFeeStatus;
	}

	public String getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(String numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
	
}
