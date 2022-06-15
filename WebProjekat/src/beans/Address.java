package beans;

public class Address {
	private int number;
	private String street;
	private String city;
	private int zipCode;
	
	public Address() {
		
	}
	
	public Address(int number, String street, String city, int zipCode) {
		super();
		this.number = number;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
