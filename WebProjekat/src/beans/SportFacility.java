package beans;

public class SportFacility {
	private String name;
	private String type; 			//enum?
	private String content; 		//????
	private boolean isWorking;
	private Location location;
	private String image; 			///??
	private int workTime; 			//??
	
	public SportFacility()
	{
		
	}
	
	public SportFacility(String name, String type, String content, boolean isWorking, Location location, String image,
			int workTime) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
		this.isWorking = isWorking;
		this.location = location;
		this.image = image;
		this.workTime = workTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
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

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	
}
