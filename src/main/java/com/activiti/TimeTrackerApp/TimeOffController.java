package com.activiti.TimeTrackerApp;

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

@RestController("VacationTimeOffController")
@EnableAutoConfiguration
@RequestMapping(value="/vacationTimeOff")
public class TimeOffController {
	
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

	
	@RequestMapping(value="/{empId}", method=RequestMethod.GET)
    public Employee getEmployeeById(@PathVariable Long empId) {
		return employeeRepository.findOne(empId);
    }

    @RequestMapping(value="/{employees}", method=RequestMethod.GET)
   public List<Employee> getEmployees() {
		return timeOffAppService.findAll();
    }  
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public MyRestEndpointResponse executeCustonLogic() {
    	
    	 Employee currentUser = null;
    	
    	List<Employee> currentEmpList = timeOffAppService.findAll();
    	for(Employee foundEmployee:currentEmpList){
    		currentUser = foundEmployee;	
    	}
        /* User userLoggedIn = SecurityUtils.getCurrentUserObject();*/
         
        long taskCount = taskService.createTaskQuery().taskAssignee(String.valueOf(currentUser.getId())).count();

        MyRestEndpointResponse myRestEndpointResponse = new MyRestEndpointResponse();
        
        myRestEndpointResponse.setFullName(currentUser.getFirstName());
        
        myRestEndpointResponse.setTaskCount(taskCount);
        
        return myRestEndpointResponse;

    }
}
