package beans;

import enums.TrainingType;

public class Training {
	private String name;
	private TrainingType trainingType;
	private SportFacility sportFacility;
	private int durationInMinutes;
	private Coach coach;
	private String description;
	private String image;
	
	public Training() {
	}

	public Training(String name, TrainingType trainingType, SportFacility sportFacility, int durationInMinutes,
			Coach coach, String description, String image) {
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

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
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
