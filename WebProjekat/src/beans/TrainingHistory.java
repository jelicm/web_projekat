package beans;

public class TrainingHistory {
	private String applicationDateAndTime;
	private Training training;
	private Customer customer;
	private Coach coach;
	
	public TrainingHistory() {
	}

	public TrainingHistory(String applicationDateAndTime, Training training, Customer customer,
			Coach coach) {
		super();
		this.applicationDateAndTime = applicationDateAndTime;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
	}

	public String getApplicationDateAndTime() {
		return applicationDateAndTime;
	}

	public void setApplicationDateAndTime(String applicationDateAndTime) {
		this.applicationDateAndTime = applicationDateAndTime;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	
}
