package beans;

import enums.FacilityContent;
import enums.FacilityType;
import enums.Status;

public class SportFacility {
	private String name;
	private FacilityType type;
	private FacilityContent content; 
	private Status status;
	private Location location;
	private String image;
	private double averageRating;
	private String workTime;
	
	public SportFacility()
	{
		
	}
	
	public SportFacility(String name, FacilityType type, FacilityContent content, Status status, Location location, String image,
			double averageRating, String workTime) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
		this.status = status;
		this.location = location;
		this.image = image;
		this.averageRating = averageRating;
		this.workTime = workTime;
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

	public FacilityContent getContent() {
		return content;
	}

	public void setContent(FacilityContent content) {
		this.content = content;
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
