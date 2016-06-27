package com.activiti.TimeTrackerApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffAppService;
import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;
import com.activiti.TimeTrackerApp.domain.Employee;

@Component("insertEmployeeVacationBean")
public class InsertEmployeeDelegate implements JavaDelegate {
	
	final static Logger logger = Logger.getLogger(InsertEmployeeDelegate.class);
	
	private static final String userLoggedIn = "narranil";

	@Autowired
	protected TimeOffAppService timeOffAppService;

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	private Employee aEmployee;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Execution Value From SpringBeanApplication:::" + execution.getId());
		Object userObj = execution.getVariable("userOutput");
		Employee emp = null;
		if (userObj ==null) {
			emp = new Employee();
			emp.setFirstName("Test12");
			emp.setLastName("Test12");
			emp.setEmail("test12@gmail.com");
			emp.setAvail_annual_leaves(11);
			emp.setAvail_sick_leaves(5);
			emp.setUserName("test12");
			emp.setTotal_vacation_avail_days(6);
			userObj = emp;
			timeOffAppService.createEmployee(emp);
		}
		Employee createdEmployee= timeOffAppService.findById(emp.getId());
		execution.setVariable("daysOfAvailVacation", createdEmployee.getTotal_vacation_avail_days());
		logger.info("Execution Value of displayUserInfo:::" + createdEmployee.getFirstName());
	}	
}
