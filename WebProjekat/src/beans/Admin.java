package beans;

public class Admin extends User {
	
	public Admin()
	{
		
	}

	public Admin(String username, String password, String name, String surname, String geneder, String role,
			String dateOfBirth) {
		super(username, password, name, surname, geneder, role, dateOfBirth);
		
	}

	
}
