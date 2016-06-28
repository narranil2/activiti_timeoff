package com.activiti.TimeTrackerApp;

import java.util.Date;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;
import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.TimeTrackerApp.domain.TimeOffRequest;

@Component("employeeUpdateVacation")
//This delegate expression bean is used to update the employee vacation and TimeOff request details in the database once the  vacation request is approved
public class EmployeeUpdateTimeOff implements JavaDelegate {

	final static Logger logger = Logger.getLogger(EmployeeUpdateTimeOff.class);

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// Update the Employee timeoff details in the database by calling update/save on person repository
		/*User currentUser = SecurityUtils.getCurrentUserObject();*/
		try {			
			//Long vacationRequestedDays =  (Long) exec.getVariable("noOfDays");
			
			logger.info("In EmployeeUpdate Manger Execute:::"+ exec.getId());
			
			String vacationRequestedDays =  (String) exec.getVariable("noOfDays");

			Date vacationStartDate = (Date) exec.getVariable("vacationStartDate");

			Date vacationEndDate = (Date) exec.getVariable("vacationEndDate");

			int requestedDays = Integer.parseInt(vacationRequestedDays); 

			logger.info("No of days of vacation requested:::"+requestedDays);

			int noOfDaysVacationAvail = 0;

			int leavesToBeUpdated = 0;

			/*Employee employee = (Employee) runtimeService.getVariable(exec.getId(), "loggedInEmployee");*/
			
			Employee foundEmployee = (Employee) runtimeService.getVariable(exec.getId(), "employee");

			String managerComments = (String)runtimeService.getVariable(exec.getId(), "managerComments");

			logger.info("In EmployeeUpdate Manger Comments:::"+ managerComments);

			boolean  vacationStatus = (boolean)runtimeService.getVariable(exec.getId(), "vacationApproved");

			logger.info("In EmployeeUpdate vacationStatus :::"+vacationStatus);

			if (foundEmployee != null) {

				noOfDaysVacationAvail = foundEmployee.getTotal_vacation_avail_days();

				logger.info("No of days of vacation Available:::"+noOfDaysVacationAvail);

				if (Integer.parseInt(vacationRequestedDays) > noOfDaysVacationAvail) {
					leavesToBeUpdated = 0;
				} else {
					leavesToBeUpdated = (noOfDaysVacationAvail - requestedDays);
					logger.info("No of days of vacation to be updated:::"+leavesToBeUpdated);
				}
				timeOffInfoBean.updateEmployeeDetails(foundEmployee.getUserName(), leavesToBeUpdated);

				TimeOffRequest aTimeOffRequest = new TimeOffRequest();

				aTimeOffRequest.setEmployee(foundEmployee);
				aTimeOffRequest.setVacationStartDate(vacationStartDate);
				aTimeOffRequest.setVacationEndDate(vacationEndDate);
				aTimeOffRequest.setNoOfVacationDays(requestedDays);
				aTimeOffRequest.setManagerComments(managerComments);
				aTimeOffRequest.setVacationStatus(vacationStatus);

				timeOffInfoBean.insertTimeOffForEmployee(aTimeOffRequest);	

				//throw an exception so that activity will rollback the transaction
				//void completeTask(String taskId);
				//throw new ActivitiException("Could not update the database with the details::::");
				//runtimeService.startProcessInstanceByKey("Anil");
			}
			logger.info("Employee and Timeoff request tables are updated with the vacation details:::");

		} catch (Exception e) {
			logger.error("Exception occured while updating employee details:::" + e.getMessage());
		}
	}

}
