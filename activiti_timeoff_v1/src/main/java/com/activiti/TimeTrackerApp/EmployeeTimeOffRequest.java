package com.activiti.TimeTrackerApp;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.domain.Employee;

@Component("employeeVacationRequest")
public class EmployeeTimeOffRequest implements JavaDelegate {

	final static Logger logger = Logger.getLogger(EmployeeTimeOffRequest.class);

	public static int empVacationAvailableDays = 0;

	@Autowired
	private RuntimeService runtimeService;

	@Override	
	public void execute(DelegateExecution execution) throws Exception {
		
		logger.info("Execution Value for getUserInfo()::" + execution.getId());
		
		Employee aEmployee = (Employee) runtimeService.getVariable(execution.getId(), "loggedInEmployee");

		//Get the values of start form properties in a map
		/*Map<String, Object> empInputDetails = execution.getVariables();*/

		String userNameValue = (String) execution.getVariable("userName");

		logger.info("UserName from Process::" + userNameValue);

		String vacationRequestedDays =  (String) execution.getVariable("noOfDays");

		logger.info("No of days of vacation requested:::" + vacationRequestedDays);

		//Employee aEmployee = timeOffInfoBean.findEmpByUserName(userNameValue);
		//From the above employee object get the details of how many days of vacation available.....
		if (aEmployee != null) {
			//empVacationAvailableDays = aEmployee.getAvail_annual_leaves() + aEmployee.getAvail_sick_leaves();
			logger.info("Employee Detils From DB::" + aEmployee.getId());

			logger.info("Found Employe First name::" + aEmployee.getFirstName());

			empVacationAvailableDays = aEmployee.getTotal_vacation_avail_days();
			execution.setVariable("nrOfDaysVacationAvail", empVacationAvailableDays);
			execution.setVariable("noofOfDaysVacationRequested", vacationRequestedDays);
			execution.setVariable("vacationAvailDays", empVacationAvailableDays);
			execution.setVariable("empFirstName", aEmployee.getFirstName());
			execution.setVariable("empLastName", aEmployee.getLastName());
		}		

		runtimeService.setVariable(execution.getId(), "employee", aEmployee);
		
	}	
}
