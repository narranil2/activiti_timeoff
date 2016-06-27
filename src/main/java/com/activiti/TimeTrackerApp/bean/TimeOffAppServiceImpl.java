package com.activiti.TimeTrackerApp.bean;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.TimeTrackerApp.domain.TimeOffRequest;
import com.activiti.TimeTrackerApp.exception.EmployeeNotFoundException;

@Service
@Transactional(value = "timeOffTransactionManager")
public class TimeOffAppServiceImpl  implements TimeOffAppService{

	final static Logger logger = Logger.getLogger(TimeOffAppServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	TimeOffRequestRepository timeOffRequestRepository;

	@Override
	@Transactional(value = "timeOffTransactionManager")
	public void createDemoEmployees() {
		if (employeeRepository.findAll().size() == 0) {
			employeeRepository.save(new Employee("anil@gmail.com", "Anil", "Reddy", "narranil1"));
			employeeRepository.save(new Employee("anil1@gmail.com", "Anil1", "Reddy1", "narranil2"));
		}		
	}

	@Override
	@Transactional(value = "timeOffTransactionManager")
	public void createEmployee(Employee aEmployee) {		
		if(aEmployee != null){
			employeeRepository.save(aEmployee);
		}
	}


	@Override
	@Transactional(value = "timeOffTransactionManager")
	public void delete(int id) {
		Employee empToDelete = employeeRepository.findOne((long) id);		
		if (empToDelete == null)
			try {
				throw new EmployeeNotFoundException();
			} catch (EmployeeNotFoundException e) {
				logger.error("Exception occured while deleting the employee::" + e.getMessage());
			}	 

		employeeRepository.delete(empToDelete);	
	}

	@Override
	@Transactional(value = "timeOffTransactionManager")
	public List<Employee> findAll() {		
		return employeeRepository.findAll();
	}


	@Override
	public Employee findEmpByUserName(String userName) {
		logger.info("in findEmpByUserName() username input::::"+ userName);
		Employee loggedInEmployee = employeeRepository.findByUserName(userName);
		return loggedInEmployee;
	}


	@Override
	@Transactional(value = "timeOffTransactionManager")
	public int updateVacationDetailsForEmployee(int leavesToBeUpdated, String userName) {
		int empRecordsUpdated = 0;
		logger.info("in updateVacationDetailsForEmployee() leaves to be updated::::"+ leavesToBeUpdated);
		empRecordsUpdated = employeeRepository.updateVacationDetailsForEmployee(userName,leavesToBeUpdated);	
		logger.info("Employee records updated::::"+empRecordsUpdated);
		return empRecordsUpdated;
	}

	@Override
	public Employee findById(Long id) {
		Employee loggedInEmployee = null;
		if(id != null){
			loggedInEmployee = employeeRepository.getOne(id);

		}
		return loggedInEmployee;
	}

	@Override
	@Transactional(value = "timeOffTransactionManager")
	public void insertTimeOffForEmployee(TimeOffRequest aTimeOffRequest) {
		try{
			if(aTimeOffRequest != null){
				timeOffRequestRepository.saveAndFlush(aTimeOffRequest);
			}
		}catch(Exception e) {
			logger.error("Exception occured while saving the Timeoff recored for Employee::" +e.getMessage());
		}

	}

}
