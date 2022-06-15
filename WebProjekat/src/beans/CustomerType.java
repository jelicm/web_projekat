package beans;

public class CustomerType {
	private String typeName;
	private double discount;
	private int requredNumberOfPoints;
	
	public CustomerType() {
		
	}
	
	public CustomerType(String typeName, double discount, int requredNumberOfPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.requredNumberOfPoints = requredNumberOfPoints;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public int getRequredNumberOfPoints() {
		return requredNumberOfPoints;
	}
	
	public void setRequredNumberOfPoints(int requredNumberOfPoints) {
		this.requredNumberOfPoints = requredNumberOfPoints;
	}
	
}
