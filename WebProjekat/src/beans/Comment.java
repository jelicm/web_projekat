package beans;

public class Comment {
	private String customer;
	private String sportFacility;
	private String content;
	private int mark;
	
	public Comment() {
	}

	public Comment(String customer, String sportFacility, String content, int mark) {
		super();
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.content = content;
		this.mark = mark;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(String sportFacility) {
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
