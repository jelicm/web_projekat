package beans;

import java.util.ArrayList;

import enums.Gender;
import enums.Role;

public class Coach extends User {
	private ArrayList<TrainingHistory> trainingHistories;

	public Coach() {
		super();
	}

	public Coach(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.trainingHistories = new ArrayList<TrainingHistory>();
	}

	public ArrayList<TrainingHistory> getTrainingHistories() {
		return trainingHistories;
	}

	public void setTrainingHistories(ArrayList<TrainingHistory> trainingHistories) {
		this.trainingHistories = trainingHistories;
	}
	
}
