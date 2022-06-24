package beans;

import java.util.ArrayList;

import enums.FacilityContent;
import enums.FacilityType;
import enums.Status;

public class SportFacility {
	private String name;
	private FacilityType type;
	private ArrayList<String> trainings; 
	private Status status;
	private Location location;
	private String image;
	private double averageRating;
	private String workTime;
	private boolean deleted;
	
	public SportFacility()
	{
		
	}

	public SportFacility(String name, FacilityType type, Status status, Location location, String image,
			double averageRating, String workTime, boolean deleted) {
		super();
		this.name = name;
		this.type = type;
		this.trainings = new ArrayList<String>();
		this.status = status;
		this.location = location;
		this.image = image;
		this.averageRating = averageRating;
		this.workTime = workTime;
		this.deleted = deleted;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FacilityType getType() {
		return type;
	}

	public void setType(FacilityType type) {
		this.type = type;
	}

	public ArrayList<String> getTrainings() {
		return trainings;
	}

	public void setTrainings(ArrayList<String> trainings) {
		this.trainings = trainings;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	
}
