package com.activiti.TimeTrackerApp.bean;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.TimeTrackerApp.domain.TimeOffRequest;

@Component("timeOffInfoBean")
public class TimeOffInfoBean {

	final static Logger logger = Logger.getLogger(TimeOffInfoBean.class);

	private Long externalId;

	@Autowired
	private TimeOffAppService timeOffAppService;

	public void createDemoEmployees() {
		timeOffAppService.createDemoEmployees();
	}

	public void createEmployee(Employee aEmployee) {
		timeOffAppService.createEmployee(aEmployee);		
	}

	public void delete(int id) {
		timeOffAppService.delete(id);
	}

	public List<Employee> findAll() {
		return timeOffAppService.findAll();
	}

	public Employee findById(long id) {
		return timeOffAppService.findById(id);
	}

	public Employee findEmpByUserName(String userName) {
		return timeOffAppService.findEmpByUserName(userName);
	}

	public int updateEmployeeDetails(String userName, int leavesToBeUpdated) {
		int recordsUpdated = 0;
		recordsUpdated = timeOffAppService.updateVacationDetailsForEmployee(leavesToBeUpdated, userName);
		return recordsUpdated;
	}

	public void insertTimeOffForEmployee(TimeOffRequest aTimeOffRequest){
		timeOffAppService.insertTimeOffForEmployee(aTimeOffRequest);
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

}
