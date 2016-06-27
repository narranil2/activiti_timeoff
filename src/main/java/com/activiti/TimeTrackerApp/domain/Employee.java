package com.activiti.TimeTrackerApp.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee implements Serializable{

	private static final long serialVersionUID = 863425812804469846L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false)
	private Long id;

	@Column(name = "username", nullable = false)
	private String userName;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "avail_annual_leaves", nullable = false)
	private int avail_annual_leaves;

	@Column(name = "avail_sick_leaves", nullable = false)
	private int avail_sick_leaves;	

	@Column(name = "total_vacation_avail_days", nullable = false)
	private int  total_vacation_avail_days;	

	//bi-directional many-to-one association to timeoff Request
	@OneToMany(mappedBy="employee",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<TimeOffRequest> requests;

	public Employee() {
	}

	public Employee(String email, String firstName, String lastName,String userName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAvail_annual_leaves() {
		return avail_annual_leaves;
	}

	public void setAvail_annual_leaves(int avail_annual_leaves) {
		this.avail_annual_leaves = avail_annual_leaves;
	}

	public int getAvail_sick_leaves() {
		return avail_sick_leaves;
	}

	public void setAvail_sick_leaves(int avail_sick_leaves) {
		this.avail_sick_leaves = avail_sick_leaves;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	

	public int getTotal_vacation_avail_days() {
		return total_vacation_avail_days;
	}

	public void setTotal_vacation_avail_days(int total_vacation_avail_days) {
		this.total_vacation_avail_days = total_vacation_avail_days;
	}	

	public List<TimeOffRequest> getRequests() {
		return this.requests;
	}

	public void setRequests(List<TimeOffRequest> requests) {
		this.requests = requests;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}	

}
