package beans;

import enums.TypeName;

public class CustomerType {
	private TypeName typeName;
	private double discount;
	private int requredNumberOfPoints;
	
	public CustomerType() {
		
	}
	
	public CustomerType(TypeName typeName, double discount, int requredNumberOfPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.requredNumberOfPoints = requredNumberOfPoints;
	}
	
	public TypeName getTypeName() {
		return typeName;
	}
	
	public void setTypeName(TypeName typeName) {
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
