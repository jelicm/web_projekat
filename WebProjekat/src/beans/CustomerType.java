package beans;

import enums.TypeName;

public class CustomerType {
	private TypeName typeName;
	private double discount;
	private int requiredNumberOfPoints;
	
	public CustomerType() {
		
	}
	
	public CustomerType(TypeName typeName, double discount, int requiredNumberOfPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.requiredNumberOfPoints = requiredNumberOfPoints;
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
	
	public int getRequiredNumberOfPoints() {
		return requiredNumberOfPoints;
	}
	
	public void setRequiredNumberOfPoints(int requiredNumberOfPoints) {
		this.requiredNumberOfPoints = requiredNumberOfPoints;
	}
	
}
