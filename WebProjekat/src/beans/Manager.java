package beans;

public class Manager extends User {
	private SportFacility sportFacility;

	public Manager() {
		super();
	}
	
	public Manager(String username, String password, String name, String surname, String geneder, String role,
			String dateOfBirth, SportFacility sportFacility) {
		super(username, password, name, surname, geneder, role, dateOfBirth);
		this.sportFacility = sportFacility;
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}
	
	
}
