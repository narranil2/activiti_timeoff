package com.activiti.TimeTrackerApp;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;

@Component("vacationManagerApproval")
public class ManagerApproval implements JavaDelegate {

	final static Logger logger = Logger.getLogger(ManagerApproval.class);

	@Autowired
	private RuntimeService runtimeService;	

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Automatically/Manually Approve the request once the process enters here and send confirmation mail
		logger.info("Under Execute Method in Manager Approval");
		boolean vacationApproved = true;
		String managerComments = (String) execution.getVariable("comments");	
		logger.info("Manager Comments under Approval::::::" + managerComments);
		runtimeService.setVariable(execution.getId(), "managerComments", managerComments);
		runtimeService.setVariable(execution.getId(), "vacationApproved", vacationApproved);		
	}	
}
