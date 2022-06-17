package beans;

public class Comment {
	private Customer customer;
	private SportFacility sportFacility;
	private String content;
	private int mark;
	
	public Comment() {
	}

	public Comment(Customer customer, SportFacility sportFacility, String content, int mark) {
		super();
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.content = content;
		this.mark = mark;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	
	
}