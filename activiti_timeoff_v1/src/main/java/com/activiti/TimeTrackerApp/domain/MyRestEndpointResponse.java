package com.activiti.TimeTrackerApp.domain;

public class MyRestEndpointResponse {

	private String fullName;
	
	private long taskCount;
	
	private String description;
	
	private String email;
	
	public MyRestEndpointResponse() {
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public long getTaskCount() {
		return taskCount;
	}
	
	public void setTaskCount(long taskCount) {
		this.taskCount = taskCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

}
