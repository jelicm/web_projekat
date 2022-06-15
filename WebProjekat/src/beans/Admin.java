package beans;

import enums.Gender;
import enums.Role;

public class Admin extends User {
	
	public Admin(){
		super();
	}

	public Admin(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		
	}
}
