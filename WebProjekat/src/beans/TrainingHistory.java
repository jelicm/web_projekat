package beans;

public class TrainingHistory {
	private String name;
	private String applicationDateAndTime;
	private String trainingDateAndTime;
	private String training;
	private String customer;
	private String coach;
	
	public TrainingHistory() {
	}

	public TrainingHistory(String applicationDateAndTime, String trainingDateAndTime, String training, String customer,
			String coach, String name) {
		super();
		this.trainingDateAndTime = trainingDateAndTime;
		this.applicationDateAndTime = applicationDateAndTime;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
		this.name = name;
	}

	public String getTrainingDateAndTime() {
		return trainingDateAndTime;
	}

	public void setTrainingDateAndTime(String trainingDateAndTime) {
		this.trainingDateAndTime = trainingDateAndTime;
	}

	public String getApplicationDateAndTime() {
		return applicationDateAndTime;
	}

	public void setApplicationDateAndTime(String applicationDateAndTime) {
		this.applicationDateAndTime = applicationDateAndTime;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
