package beans;

import enums.Permission;

public class Comment {
	private String name;
	private String customer;
	private String sportFacility;
	private String content;
	private int mark;
	private Permission permission;

	public Comment() {
	}

	public Comment(String name, String customer, String sportFacility, String content, int mark, Permission permission) {
		super();
		this.name = name;
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.content = content;
		this.mark = mark;
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(String sportFacility) {
		this.sportFacility = sportFacility;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	
	
}
