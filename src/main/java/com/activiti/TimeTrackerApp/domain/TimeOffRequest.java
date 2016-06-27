package com.activiti.TimeTrackerApp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "timeofffrequest")
public class TimeOffRequest implements Serializable{
	
	private static final long serialVersionUID = -4521857501921892840L;	
	
	@Id
	@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN", initialValue=100, allocationSize=12)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq-gen")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "vacation_start_date", nullable = false)
	private Date vacationStartDate;

	@Column(name = "vacation_end_date", nullable = false)
	private Date vacationEndDate;

	@Column(name = "vacation_status", nullable = true)
	private boolean  vacationStatus;
	
	@Column(name = "no_of_vacation_days", nullable = false)
	private int noOfVacationDays;
	
	@Column(name = "manager_comments", nullable = false)
	private String managerComments;
	
	//bi-directional many-to-one association to employee
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="employee_id")
    private Employee employee;
    
    public TimeOffRequest() {
    	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVacationStartDate() {
		return vacationStartDate;
	}

	public void setVacationStartDate(Date vacationStartDate) {
		this.vacationStartDate = vacationStartDate;
	}

	public Date getVacationEndDate() {
		return vacationEndDate;
	}

	public void setVacationEndDate(Date vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}

	public boolean isVacationStatus() {
		return vacationStatus;
	}

	public void setVacationStatus(boolean vacationStatus) {
		this.vacationStatus = vacationStatus;
	}

	public int getNoOfVacationDays() {
		return noOfVacationDays;
	}

	public void setNoOfVacationDays(int noOfVacationDays) {
		this.noOfVacationDays = noOfVacationDays;
	}

	public String getManagerComments() {
		return managerComments;
	}

	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}	
    
}
