package com.activiti.TimeTrackerApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.identity.Authentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.bean.TimeOffInfoBean;
import com.activiti.api.security.LoginListener;

@Component("loginListener")
public class MyLoginListener implements LoginListener, org.activiti.engine.delegate.ExecutionListener,ActivitiEventListener {

	private static final long serialVersionUID = 1603302501148303037L;

	final static Logger logger = Logger.getLogger(MyLoginListener.class);

	@Autowired
	private TimeOffInfoBean timeOffInfoBean;

	@Override
	public void onLogin(com.activiti.domain.idm.User user) {

		String currentLoggedUser=Authentication.getAuthenticatedUserId();

		logger.info("User " + currentLoggedUser + " has logged in");

		logger.info("User " + user.getFirstName() + " has logged in");

		if(user.getExternalId()  != null){

			logger.info("Logged in User External Id:::" + user.getExternalId() + " has logged in");
			
			timeOffInfoBean.setExternalId(Long.parseLong(user.getExternalId()));
		}	

	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		logger.info("In notify() login listener:::");	
		
		//logger.info("Timeoff Bean externalId::::::" + timeOffInfoBean.getExternalId());	

		/*if(timeOffInfoBean.getExternalId() != null){

			logger.info("In notify() if loop:::::");

			loggedInEmployee = timeOffInfoBean.findById(timeOffInfoBean.getExternalId());

			logger.info("Logged in User Email::::::" + loggedInEmployee.getEmail());			

			runtimeService.setVariable(execution.getId(), "daysOfAvailVacation", loggedInEmployee.getTotal_vacation_avail_days());

			runtimeService.setVariable(execution.getId(), "loggedInEmployee", loggedInEmployee);
		}*/		

	}

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onEvent(ActivitiEvent event) {
		logger.info("In onEvent():::");
		
	}	
}

