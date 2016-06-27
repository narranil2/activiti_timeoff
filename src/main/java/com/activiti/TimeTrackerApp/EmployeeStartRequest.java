package com.activiti.TimeTrackerApp;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;
import com.activiti.TimeTrackerApp.domain.Employee;

@Component("employeeVacationStart")
public class EmployeeStartRequest implements JavaDelegate{

	final static Logger logger = Logger.getLogger(EmployeeTimeOffRequest.class);

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	@Autowired
	private RuntimeService runtimeService;

	private Employee loggedInEmployee;

	@Override	
	public void execute(DelegateExecution execution) throws Exception {

		if(timeOffInfoBean.getExternalId() != null){

			logger.info("External ID from Info Bean in EmployeeStartRequest is*******************" + timeOffInfoBean.getExternalId());

			loggedInEmployee = timeOffInfoBean.findById(timeOffInfoBean.getExternalId());

			if(loggedInEmployee != null){

				runtimeService.setVariable(execution.getId(), "daysOfAvailVacation", loggedInEmployee.getTotal_vacation_avail_days());

				runtimeService.setVariable(execution.getId(), "empFirstName", loggedInEmployee.getFirstName());

				runtimeService.setVariable(execution.getId(), "empLastName", loggedInEmployee.getLastName());

				runtimeService.setVariable(execution.getId(), "loggedInEmployee", loggedInEmployee);

				runtimeService.setVariable(execution.getId(), "daysOfAvailVacation", loggedInEmployee.getTotal_vacation_avail_days());
			}
		}

	}

}
