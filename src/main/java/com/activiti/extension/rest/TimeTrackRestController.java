package com.activiti.extension.rest;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.TimeTrackerApp.bean.EmployeeRepository;
import com.activiti.TimeTrackerApp.bean.TimeOffAppService;
import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.TimeTrackerApp.domain.MyRestEndpointResponse;

@RestController("TimeOffController")
@EnableAutoConfiguration
@RequestMapping(value="/timeOff")
public class TimeTrackRestController {

	@Autowired
	private TimeOffAppService timeOffAppService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TaskService taskService;

	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable String name) {
		return "Hello, " + name + "! Welcome to Timeoff Application";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String home() {
		return "Hello Anil Welcome to Time Track App!";
	}


	@RequestMapping(value="/employee/{empId}", method=RequestMethod.GET, produces = "application/json")
	public Employee getEmployeeById(@PathVariable Long empId) {
		Employee seletedEmployee = null;
		seletedEmployee = employeeRepository.findOne(empId);	
		Employee aEmployee = new Employee();
		aEmployee.setId(seletedEmployee.getId());
		aEmployee.setFirstName(seletedEmployee.getFirstName());
		aEmployee.setLastName(seletedEmployee.getLastName());
		aEmployee.setTotal_vacation_avail_days(seletedEmployee.getTotal_vacation_avail_days());
		return aEmployee;
	}

	@RequestMapping(value="/employees", method=RequestMethod.GET, produces = "application/json")
	public List<Employee> getEmployees() {
		List<Employee> employeeList = null;
		//Employee returnedEmployee = null;
		for(Employee aEmployee:timeOffAppService.findAll()){
			employeeList = new ArrayList<Employee>();
			employeeList.add(aEmployee);
		}
		return employeeList;
	}  
	
	@RequestMapping(value="/getEmployeeNames", method=RequestMethod.GET, produces = "application/json")
	public List<String> getEmployeeNames() {
		List<String> employeeNames = new ArrayList<String>();

		List<Employee> currentEmpList = timeOffAppService.findAll();
		for(Employee foundEmployee:currentEmpList){
			employeeNames.add(foundEmployee.getFirstName());	
		}
		return employeeNames;
	}  

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public MyRestEndpointResponse executeCustomLogic() {

		Employee currentUser = null;

		List<Employee> currentEmpList = timeOffAppService.findAll();
		for(Employee foundEmployee:currentEmpList){
			currentUser = foundEmployee;	
		}
		// User currentUser = SecurityUtils.getCurrentUserObject();
		long taskCount = taskService.createTaskQuery().taskAssignee(String.valueOf(currentUser.getId())).count();

		MyRestEndpointResponse myRestEndpointResponse = new MyRestEndpointResponse();
		myRestEndpointResponse.setFullName(currentUser.getFirstName());
		myRestEndpointResponse.setTaskCount(taskCount);
		myRestEndpointResponse.setEmail(currentUser.getEmail());
		return myRestEndpointResponse;

	}

}
