package beans;

import enums.TrainingType;

public class Training {
	private String name;
	private TrainingType trainingType;
	private String sportFacility;
	private int durationInMinutes;
	private String coach;
	private String description;
	private String image;
	
	public Training() {
	}

	public Training(String name, TrainingType trainingType, String sportFacility, int durationInMinutes,
			String coach, String description, String image) {
		super();
		this.name = name;
		this.trainingType = trainingType;
		this.sportFacility = sportFacility;
		this.durationInMinutes = durationInMinutes;
		this.coach = coach;
		this.description = description;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public String getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(String sportFacility) {
		this.sportFacility = sportFacility;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
