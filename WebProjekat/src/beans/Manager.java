package beans;

import enums.Gender;
import enums.Role;

public class Manager extends User {
	private String sportFacility;
	private boolean deleted;

	public Manager() {
		super();
	}
	
	public Manager(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth, String sportFacility, boolean deleted) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.deleted = deleted;
		this.sportFacility = sportFacility;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(String sportFacility) {
		this.sportFacility = sportFacility;
	}
	
	
}
