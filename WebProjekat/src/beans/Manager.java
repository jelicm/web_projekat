package beans;

import enums.Gender;
import enums.Role;

public class Manager extends User {
	private SportFacility sportFacility;

	public Manager() {
		super();
	}
	
	public Manager(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth, SportFacility sportFacility) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.sportFacility = sportFacility;
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}
	
	
}
