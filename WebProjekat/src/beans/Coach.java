package beans;

import java.util.ArrayList;

import enums.Gender;
import enums.Role;

public class Coach extends User {
	private ArrayList<String> trainingHistories;
	private boolean deleted;

	public Coach() {
		super();
	}

	public Coach(String username, String password, String name, String surname, Gender gender, Role role,
			String dateOfBirth, boolean deleted) {
		super(username, password, name, surname, gender, role, dateOfBirth);
		this.deleted = deleted;
		this.trainingHistories = new ArrayList<String>();
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArrayList<String> getTrainingHistories() {
		return trainingHistories;
	}

	public void setTrainingHistories(ArrayList<String> trainingHistories) {
		this.trainingHistories = trainingHistories;
	}
	
	public void addTrainingHistory(String trainingHistory){
		this.trainingHistories.add(trainingHistory);
	}
	
	public void removeTrainingHistory(String trainingHistory){
		this.trainingHistories.remove(trainingHistory);
	}
	
}
