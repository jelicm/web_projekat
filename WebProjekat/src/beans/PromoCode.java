package beans;

public class PromoCode {
	private String identifier;
	private String expirationDate;
	private int numberOfUses;
	private double discount;
	
	public PromoCode() {
		
	}

	public PromoCode(String identifier, String expirationDate, int numberOfUses, double discount) {
		super();
		this.identifier = identifier;
		this.expirationDate = expirationDate;
		this.numberOfUses = numberOfUses;
		this.discount = discount;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getNumberOfUses() {
		return numberOfUses;
	}

	public void setNumberOfUses(int numberOfUses) {
		this.numberOfUses = numberOfUses;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
