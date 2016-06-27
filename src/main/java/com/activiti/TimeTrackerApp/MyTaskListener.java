package com.activiti.TimeTrackerApp;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;
import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.service.api.UserService;

@Component("myTaskListener")
public class MyTaskListener implements org.activiti.engine.delegate.TaskListener, JavaDelegate {

	private static final long serialVersionUID = 4188150072702659649L;

	final static Logger logger = Logger.getLogger(MyTaskListener.class);

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void notify(DelegateTask delegateTask) {	 
		
		logger.info("In MyTaskListener()::::::");	

		String initiatorId = (String) delegateTask.getVariable("initiator");

		Long initiatorIdLong = Long.parseLong(initiatorId);

		com.activiti.domain.idm.User initiatorUser = userService.findUser(initiatorIdLong);

		System.out.println("initiator Email::::::: = " + initiatorUser.getEmail());

		System.out.println("Logged in User External Id from Tak Listener = " + initiatorUser.getExternalId());

		Employee loggedInEmployee = timeOffInfoBean.findById(Long.parseLong(initiatorUser.getExternalId()));

		logger.info("Logged in Employee details from hrdatabase"+ loggedInEmployee.toString());

		delegateTask.setVariable("daysOfAvailVacation", loggedInEmployee.getTotal_vacation_avail_days());
		
		runtimeService.setVariable(delegateTask.getId(), "loggedInEmployee", loggedInEmployee);
	}

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
	}

}
